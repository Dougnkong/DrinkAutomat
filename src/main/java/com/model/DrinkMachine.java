package com.model;

import com.interfaces_and_enum.AbstractAutomat;
import com.interfaces_and_enum.EnumCoinValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * This class is an implementation of a drink machine, with the principal procedure.
 */
public class DrinkMachine extends AbstractAutomat {
    public static final String NOT_MORE_AVAILABLE = "The drink name you choose is not more available";
    public static final String NOT_ENOUGH_MONEY = "You have not given enough coins for the drink you need.";
    public static final String NOT_ENOUGH_COINS_FOR_CHANGE = "Not enough coins for change";


    HashMap<String, Compartment> compartments;
    List<CoinAndQuantity> availableCoinForChange;
    List<CoinAndQuantity> returnMoney;


    /**
     * @param compartments: Each compartment contents only one drink type.
     */
    public DrinkMachine(HashMap<String, Compartment> compartments) {
        this.returnMoney = new ArrayList<>();
        this.availableCoinForChange = new ArrayList<>();
        this.compartments = compartments;
    }

    public DrinkMachine() {
    }

    /**
     * This method manages the purchase process and in the best case returns
     * an object containing the order and the exchange coin.
     *
     * @param drink: Contains the drink the com.user choose.
     * @param coins: Contains the list of coins given for the com.user.
     * @return if success the com.user's oder.
     */
    public DrinkAndChange buy(Drink drink, List<Coin> coins) {
        int rest;
        if (!this.compartments.containsKey(drink.getName())) {

            System.out.println(NOT_MORE_AVAILABLE);

            return null;
        }
        if (isNotEnoughCoinsGiven(drink, coins)) {

            System.out.println(NOT_ENOUGH_MONEY);

            return null;
        }

        this.fillInWithCoin(coins);

        rest = getCoinSum(coins) - (int) Math.round(this.compartments.get(drink.getName()).getCommon().getPrice() * 100);

        final List<CoinAndQuantity> restCoinToGiveBack = changeCoin(rest);

        if (this.getSumAvailableChangeCoins(restCoinToGiveBack) < rest) {

            System.out.println(NOT_ENOUGH_COINS_FOR_CHANGE);

            fillOutInputCoin(coins);

            return null;
        }

        this.fillOut(restCoinToGiveBack);

        return new DrinkAndChange(drink, restCoinToGiveBack);
    }

    /**
     * This method takes the sum of the entered coins and returns a coin change list.
     *
     * @param inputSum: Is the sum of input coin.
     * @return A list of change coins.
     */
    public List<CoinAndQuantity> changeCoin(final int inputSum) {
        CoinAndQuantity tmpCoinAndToReturn;
        CoinAndQuantity availableCoinWithTypeOfCoin;
        int currentRest = inputSum;

        Collections.sort(this.availableCoinForChange);

        if (this.availableCoinForChange.stream().findFirst().isPresent()) ;

        int tmpMinCoinTypeValue = this.availableCoinForChange.stream().findFirst().get().getType().getValue();

        do {
            for (int i = this.availableCoinForChange.size() - 1; i >= 0; i--) {

                availableCoinWithTypeOfCoin = this.availableCoinForChange.get(i);

                int currentCoinNumber = availableCoinWithTypeOfCoin.getType().getValue();

                int wholePart = currentRest / currentCoinNumber;

                if (wholePart > 0 && availableCoinWithTypeOfCoin.getQuantity() != 0) {

                    if (wholePart > availableCoinWithTypeOfCoin.getQuantity()) {

                        tmpCoinAndToReturn = new CoinAndQuantity(availableCoinWithTypeOfCoin.getType(), availableCoinWithTypeOfCoin.getQuantity());

                    } else {

                        tmpCoinAndToReturn = new CoinAndQuantity(availableCoinWithTypeOfCoin.getType(), wholePart);
                    }
                    this.returnMoney.add(tmpCoinAndToReturn);
                }

                currentRest = currentRest - wholePart * currentCoinNumber;

                this.updateAvailableCoinForChange();
            }
        }
        while (currentRest >= tmpMinCoinTypeValue && currentRest > 0);
        return returnMoney;
    }

    /**
     * Remove coins with the quantity zero
     */
    public void updateAvailableCoinForChange() {

        for (int i = this.availableCoinForChange.size() - 1; i >= 0; i--) {

            if (this.availableCoinForChange.get(i).getQuantity() == 0) {

                this.availableCoinForChange.remove(this.availableCoinForChange.get(i));
            }
        }
    }

    public void addAvailableCoinForChange(CoinAndQuantity availableCoinForChange) {

        this.availableCoinForChange.add(availableCoinForChange);

    }

    /**
     * @param coinAndQuantities contain each available coin for change and his quantity.
     * @return sum of all coins value.
     */
    public int getSumAvailableChangeCoins(List<CoinAndQuantity> coinAndQuantities) {

        int tmpSum = 0;

        for (CoinAndQuantity coinAndQuantity : coinAndQuantities) {

            tmpSum += coinAndQuantity.type.getValue() * coinAndQuantity.getQuantity();
        }
        return tmpSum;
    }

    /**
     * This method fill change coins in the machine
     *
     * @param coinsToFillIn
     */
    public void fillInWithCoin(List<Coin> coinsToFillIn) {

        CoinAndQuantity availableCoinWithTypeOfCoin;

        for (Coin coin : coinsToFillIn) {

            availableCoinWithTypeOfCoin = this.getCoinAndQuantityByType(coin.coinValue);

            if (availableCoinWithTypeOfCoin == null) {

                this.availableCoinForChange.add(new CoinAndQuantity(coin.coinValue, 1));

            } else {
                availableCoinWithTypeOfCoin.setQuantity(availableCoinWithTypeOfCoin.quantity + 1);
            }
        }
    }

    public List<CoinAndQuantity> getAvailableCoinForChange() {
        return availableCoinForChange;
    }

    public HashMap<String, Compartment> getCompartments() {
        return compartments;
    }

    /**
     * This method removes input coins from the available change coins list.
     *
     * @param inputCoins the coin  giving from the com.user.
     */
    public void fillOutInputCoin(List<Coin> inputCoins) {

        CoinAndQuantity availableCoinWithTypeOfCoin;
        int tmpIndex;

        for (Coin coin : inputCoins) {

            availableCoinWithTypeOfCoin = this.getCoinAndQuantityByType(coin.coinValue);

            if (availableCoinWithTypeOfCoin != null) {

                int updatedQuantity = availableCoinWithTypeOfCoin.quantity - 1;

                if (updatedQuantity == 0) {

                    this.availableCoinForChange.remove(availableCoinWithTypeOfCoin);
                }

                availableCoinWithTypeOfCoin.setQuantity(updatedQuantity);

            }
        }
    }

    /**
     * This method removes changes coins in save before delivering the order.
     * This occurs when the machine releases the remaining coins to the current com.user.
     *
     * @param restCoinToGiveBack is a list of change coins to give back to the current com.user.
     */
    public void fillOut(List<CoinAndQuantity> restCoinToGiveBack) {

        CoinAndQuantity availableCoinWithTypeOfCoin;

        for (CoinAndQuantity coin : restCoinToGiveBack) {

            availableCoinWithTypeOfCoin = this.getCoinAndQuantityByType(coin.type);

            if (availableCoinWithTypeOfCoin != null) {

                int updatedQuantity = availableCoinWithTypeOfCoin.getQuantity() - coin.quantity;

                if (updatedQuantity == 0) {

                    this.availableCoinForChange.remove(availableCoinWithTypeOfCoin);

                } else {

                    availableCoinWithTypeOfCoin.setQuantity(updatedQuantity);

                }
            }
        }
    }

    /**
     * The method returns CoinAndQuantity object of available coin for change list by given coin value.
     *
     * @param coinValue to find.
     * @return founded CoinAndQuantity object
     */
    private CoinAndQuantity getCoinAndQuantityByType(final EnumCoinValue coinValue) {

        for (CoinAndQuantity coinAndQuantity : this.availableCoinForChange) {

            if (coinAndQuantity.type.equals(coinValue)) {

                return coinAndQuantity;
            }
        }
        return null;
    }

    private int getCoinSum(final List<Coin> coins) {
        int sumCoin = 0;

        for (Coin coin : coins) {
            sumCoin += coin.getCoinValue().getValue();
        }

        return sumCoin;
    }

    /**
     * @param drink
     * @param coins
     * @return Price bigger than given coins (=true) false else.
     */
    private boolean isNotEnoughCoinsGiven(Drink drink, final List<Coin> coins) {

        return drink.getPrice() * 100 > getCoinSum(coins);

    }
}