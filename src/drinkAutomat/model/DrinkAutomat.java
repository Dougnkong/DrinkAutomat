package drinkAutomat.model;

import drinkAutomat.common.Automat;
import drinkAutomat.common.Coin;
import drinkAutomat.common.Drink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DrinkAutomat extends Automat {
    public static final String NOT_MORE_AVAILABLE = "The drink name you choose is not more available";
    public static final String NOT_ENOUGH_MONEY = "You do not give enough coins for the drink you need.";
    public static final String NOT_ENOUGH_COINS_FOR_CHANGE = "Not enough coins for change";

    HashMap<String, Compartment> compartments;
    List<CoinAndQuantity> availableCoinForChange;
    List<CoinAndQuantity> returnMoney;

    public DrinkAutomat(HashMap<String, Compartment> compartments) {
        this.returnMoney = new ArrayList<>();
        this.availableCoinForChange = new ArrayList<>();
        this.compartments = compartments;
    }

    private int getCoinSum(List<Coin> coins) {
        int sumCoin = 0;

        for (Coin coin : coins) {
            sumCoin += coin.getCoinType();
        }

        return sumCoin;
    }

    private boolean isNotEnoughCoinsGiven(Drink drink, List<Coin> coins) {

        return drink.getPrice() * 100 > getCoinSum(coins);

    }

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

        List<CoinAndQuantity> restCoinToGiveBack = changeCoin(rest);

        if (this.getSumAvailableChangeCoins(restCoinToGiveBack) < rest) {

            System.out.println(NOT_ENOUGH_COINS_FOR_CHANGE);

            fillOutInputCoin(coins);

            return null;
        }

        this.fillOut(restCoinToGiveBack);

        return new DrinkAndChange(drink, restCoinToGiveBack);
    }

    public List<CoinAndQuantity> changeCoin(int sum) {
        CoinAndQuantity tmpCoinToReturn;
        CoinAndQuantity tmpCoin;
        int tmpRest = sum;

        Collections.sort(this.availableCoinForChange);

        if (this.availableCoinForChange.stream().findFirst().isPresent()) ;

        int tmpMinCoinTypeValue = this.availableCoinForChange.stream().findFirst().get().getType().getCoinType();

        do {
            for (int i = this.availableCoinForChange.size() - 1; i >= 0; i--) {

                tmpCoin = this.availableCoinForChange.get(i);

                int tmpNumber = tmpCoin.getType().getCoinType();

                int tmpPart = tmpRest / tmpNumber;

                if (tmpPart > 0 && tmpCoin.getQuantity() != 0) {

                    if (tmpPart > tmpCoin.getQuantity()) {

                        tmpCoinToReturn = new CoinAndQuantity(tmpCoin.getType(), tmpCoin.getQuantity());

                    } else {

                        tmpCoinToReturn = new CoinAndQuantity(tmpCoin.getType(), tmpPart);
                    }
                    this.returnMoney.add(tmpCoinToReturn);
                }
                System.out.println(tmpRest);
                tmpRest = tmpRest - tmpPart * tmpNumber;
                this.updateAvailableCoinForChange();
            }
        }
        while (tmpRest >= tmpMinCoinTypeValue && tmpRest > 0);
        return returnMoney;
    }

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

    public int getSumAvailableChangeCoins(List<CoinAndQuantity> coinAndQuantities) {

        int tmpSum = 0;

        for (CoinAndQuantity coinAndQuantity : coinAndQuantities) {

            tmpSum += coinAndQuantity.type.getCoinType() * coinAndQuantity.getQuantity();

        }

        return tmpSum;
    }

    /**
     * Fill coins for change in the machine
     */
    public void fillInWithCoin(List<Coin> coinsToFillIn) {

        CoinAndQuantity tmpCoinAndQuantity;
        int tmpIndex;

        for (Coin coinType : coinsToFillIn) {

            if (!this.availableCoinForChange.contains(coinType)) {

                this.availableCoinForChange.add(new CoinAndQuantity(coinType, 1));

            } else {

                tmpIndex = this.availableCoinForChange.indexOf(coinType);

                if (tmpIndex != -1) {

                    tmpCoinAndQuantity = this.availableCoinForChange.get(tmpIndex);

                    tmpCoinAndQuantity.setQuantity(tmpCoinAndQuantity.quantity + 1);

                    this.availableCoinForChange.set(tmpIndex, tmpCoinAndQuantity);
                }
            }
        }
    }

    public List<CoinAndQuantity> getAvailableCoinForChange() {

        return availableCoinForChange;

    }

    public void fillOutInputCoin(List<Coin> inputCoins) {

        CoinAndQuantity tmpCoinAndQuantity;
        int tmpIndex;

        for (Coin coinType : inputCoins) {

            if (this.availableCoinForChange.contains(coinType)) {

                tmpIndex = this.availableCoinForChange.indexOf(coinType);

                if (tmpIndex != -1) {

                    tmpCoinAndQuantity = this.availableCoinForChange.get(tmpIndex);

                    int tmpCoinQuantity = tmpCoinAndQuantity.quantity - 1;

                    if (tmpCoinQuantity == 0) {

                        this.availableCoinForChange.remove(tmpIndex);

                    }

                    tmpCoinAndQuantity.setQuantity(tmpCoinQuantity);

                    this.availableCoinForChange.set(tmpIndex, tmpCoinAndQuantity);
                }
            }
        }
    }

    /**
     * Remove changes coin
     */
    public void fillOut(List<CoinAndQuantity> restCoinToGiveBack) {

        CoinAndQuantity tmpCoinAndQuantity;
        int tmpIndex;

        for (CoinAndQuantity coin : restCoinToGiveBack) {

            tmpIndex = this.availableCoinForChange.indexOf(coin.type);

            if (tmpIndex != -1) {

                tmpCoinAndQuantity = this.availableCoinForChange.get(tmpIndex);

                int tmpCoinQuantity = tmpCoinAndQuantity.getQuantity() - coin.quantity;

                if (tmpCoinQuantity == 0) {

                    this.availableCoinForChange.remove(tmpIndex);

                } else {

                    tmpCoinAndQuantity.setQuantity(tmpCoinQuantity);

                    this.availableCoinForChange.set(tmpIndex, tmpCoinAndQuantity);
                }
            }
        }
    }
}
