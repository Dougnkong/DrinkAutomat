package drinkAutomatTest;

import drinkAutomat.common.Coin;
import drinkAutomat.common.Drink;
import drinkAutomat.model.Compartment;
import drinkAutomat.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testAutomate {

    public static final String COLA = "Cola";
    public static final String BIER = "Bier";
    Drink cola;
    Drink bier;
    Compartment<Drink> colaCompartment;
    Compartment<Drink> bierCompartment;
    HashMap<String, Compartment> compartments;

    @BeforeEach
    private void init() {
        cola = new Drink(COLA, 1.20);
        bier = new Drink(BIER, 2);
        colaCompartment = new Compartment<>(cola, 2);
        bierCompartment = new Compartment<>(bier, 2);
        compartments = new HashMap<>();
        compartments.put(cola.getName(), colaCompartment);
        compartments.put(bier.getName(), bierCompartment);
    }

    @Test
    public void testExactMoney() {
        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(Coin.FIFTY_CENT);
        inputCoins.add(Coin.FIFTY_CENT);
        inputCoins.add(Coin.ONE_EURO);

        CoinAndQuantity coin_50 = new CoinAndQuantity(Coin.FIFTY_CENT, 6);
        CoinAndQuantity coin_20 = new CoinAndQuantity(Coin.TWENTY_CENT, 2);
        CoinAndQuantity coin_10 = new CoinAndQuantity(Coin.TEN_CENT, 2);
        drinkAutomat.addAvailableCoinForChange(coin_10);
        drinkAutomat.addAvailableCoinForChange(coin_20);
        drinkAutomat.addAvailableCoinForChange(coin_50);

        DrinkAndChange drinkAndChange = drinkAutomat.buy(bier, inputCoins);

        assertNotSame(drinkAndChange.getDrinkName(), cola.getName());
    }

    @Test
    /** Case where the user Automate have to give back 1x50 cent, 1x20 cent and 1x10 cent*/
    public void testReturnMoney2() {
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(Coin.TWO_EURO);

        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);

        /**Update coin for change*/
        CoinAndQuantity coin_50 = new CoinAndQuantity(Coin.FIFTY_CENT, 1);
        CoinAndQuantity coin_20 = new CoinAndQuantity(Coin.TWENTY_CENT, 1);
        CoinAndQuantity coin_10 = new CoinAndQuantity(Coin.TEN_CENT, 7);
        drinkAutomat.addAvailableCoinForChange(coin_10);
        drinkAutomat.addAvailableCoinForChange(coin_20);
        drinkAutomat.addAvailableCoinForChange(coin_50);

        DrinkAndChange drinkAndChange = drinkAutomat.buy(cola, inputCoins);

        assertEquals(4, drinkAutomat.getAvailableCoinForChange().size());
        assertEquals(cola.getName(), drinkAndChange.getDrinkName());
        assertEquals(1, drinkAndChange.getChange().get(Coin.FIFTY_CENT));
        assertEquals(1, drinkAndChange.getChange().get(Coin.TWENTY_CENT));
        assertEquals(1, drinkAndChange.getChange().get(Coin.TEN_CENT));
    }

    @Test
    public void testNoExistingDrink() {

        HashMap<String, Compartment> compartments = new HashMap<>();

        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(Coin.TWO_EURO);

        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);

        DrinkAndChange drinkAndChange = drinkAutomat.buy(cola, inputCoins);
        assertNull(drinkAndChange);
    }

    @Test
    public void testNotEnoughCoinForDrink() {
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(Coin.ONE_EURO);

        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);

        DrinkAndChange result = drinkAutomat.buy(cola, inputCoins);

        assertNull(result);
    }

    @Test
    public void testFillInChangeCoins() {
        List<Coin> inputCoins = new ArrayList<>();

        inputCoins.add(Coin.TWO_EURO);

        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);

        assertEquals(0, drinkAutomat.getAvailableCoinForChange().size());

        drinkAutomat.fillInWithCoin(inputCoins);

        assertEquals(1, drinkAutomat.getAvailableCoinForChange().size());

        assertEquals(Coin.TWO_EURO, drinkAutomat.getAvailableCoinForChange().get(0).getType());
    }
}