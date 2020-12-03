package drinkAutomatTest;

import drinkAutomat.common.CoinValue;
import drinkAutomat.common.Drink;
import drinkAutomat.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testDrinkAutomat {

    public static final String COLA = "Cola";
    public static final String BIER = "Bier";
    Drink cola;
    Drink bier;
    Compartment<Drink> colaCompartment;
    Compartment<Drink> bierCompartment;
    HashMap<String, Compartment> compartments;


    /**
     * This function is running before each test.
     * And makes some initialization.
     */
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

    /**
     * A user orders a beer and gives exactly three coins (50, 50 and 1 euros).
     * The test checks if he gets exactly one beer and no change, because he gave exactly 2 euros.
     */
    @Test
    public void testReturnDrink() {
        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(new Coin(CoinValue.FIFTY_CENT));
        inputCoins.add(new Coin(CoinValue.FIFTY_CENT));
        inputCoins.add(new Coin(CoinValue.ONE_EURO));

        CoinAndQuantity coin_50 = new CoinAndQuantity(CoinValue.FIFTY_CENT, 6);
        CoinAndQuantity coin_20 = new CoinAndQuantity(CoinValue.TWENTY_CENT, 2);
        CoinAndQuantity coin_10 = new CoinAndQuantity(CoinValue.TEN_CENT, 2);
        drinkAutomat.addAvailableCoinForChange(coin_10);
        drinkAutomat.addAvailableCoinForChange(coin_20);
        drinkAutomat.addAvailableCoinForChange(coin_50);

        DrinkAndChange drinkAndChange = drinkAutomat.buy(bier, inputCoins);

        assertEquals(bier.getName(), drinkAndChange.getDrinkName());
        assertEquals(0, drinkAndChange.getChange().size());


    }

    /**
     * A user ordered a cola and gives 2 euro. The cola cost is 1.20 euro. The rest will be 80 cent.
     * Test if the machine gives back a cola and the following coins 1x50 cent, 1x20 cent and 1x10 cent.
     * Test further to see if there are no more 50 cents in the reserve, and also shows that
     * the 2 euro is now also in the reserve.
     */
    @Test
    public void testReturnChange() {
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(new Coin(CoinValue.TWO_EURO));

        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);

        /**
         * Set coins for change
         */
        CoinAndQuantity coin_50 = new CoinAndQuantity(CoinValue.FIFTY_CENT, 1);
        CoinAndQuantity coin_20 = new CoinAndQuantity(CoinValue.TWENTY_CENT, 4);
        CoinAndQuantity coin_10 = new CoinAndQuantity(CoinValue.TEN_CENT, 8);
        drinkAutomat.addAvailableCoinForChange(coin_10);
        drinkAutomat.addAvailableCoinForChange(coin_20);
        drinkAutomat.addAvailableCoinForChange(coin_50);

        DrinkAndChange drinkAndChange = drinkAutomat.buy(cola, inputCoins);

        assertEquals(cola.getName(), drinkAndChange.getDrinkName());
        assertEquals(1, drinkAndChange.getChange().get(CoinValue.FIFTY_CENT));
        assertEquals(1, drinkAndChange.getChange().get(CoinValue.TWENTY_CENT));
        assertEquals(1, drinkAndChange.getChange().get(CoinValue.TEN_CENT));

        assertEquals(3, drinkAutomat.getAvailableCoinForChange().size());

        for (CoinAndQuantity coinAndQuantity : drinkAutomat.getAvailableCoinForChange()) {
            assertNotEquals(CoinValue.FIFTY_CENT, coinAndQuantity.getType());
        }
    }

    /**
     * A user orders a cola. As the ordered drink is no longer available,
     * the purchase is not made. This test checks or no cola is given.
     */
    @Test
    public void testNoExistingDrink() {

        HashMap<String, Compartment> compartments = new HashMap<>();

        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(new Coin(CoinValue.TWO_EURO));

        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);

        DrinkAndChange drinkAndChange = drinkAutomat.buy(cola, inputCoins);
        assertNull(drinkAndChange);
    }

    /**
     * A user orders a cola and gives 2 euros. But a cola costs 1.20 euro.
     * As the machine has no more change he should not place the order.
     */
    @Test
    public void testNotEnoughCoinForDrink() {
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(new Coin(CoinValue.ONE_EURO));

        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);

        DrinkAndChange result = drinkAutomat.buy(cola, inputCoins);

        assertNull(result);
    }

    /**
     * This test checks the functionality of the method fillInWithCoin,
     * which has the task of refilling change in the machine.
     * The reserve is filled with ten cent and at the end of this operation 2 cent is available for change.
     */
    @Test
    public void testFillInChangeCoins() {
        List<Coin> inputCoins = new ArrayList<>();

        inputCoins.add(new Coin(CoinValue.TEN_CENT));

        DrinkAutomat drinkAutomat = new DrinkAutomat(compartments);

        assertEquals(0, drinkAutomat.getAvailableCoinForChange().size());

        drinkAutomat.fillInWithCoin(inputCoins);

        assertEquals(1, drinkAutomat.getAvailableCoinForChange().size());

        assertEquals(CoinValue.TEN_CENT, drinkAutomat.getAvailableCoinForChange().get(0).getType());
    }
}