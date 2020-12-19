
import common.CoinValue;
import common.Drink;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDrinkMachine {

    public static final String COLA = "Cola";
    public static final String BEER = "Beer";
    Drink cola;
    Drink beer;
    Compartment<Drink> colaCompartment;
    Compartment<Drink> beerCompartment;
    HashMap<String, Compartment> compartments;


    /**
     * This function is running before each test.
     * And makes some initialization.
     */
    @BeforeEach
    public void init() {
        cola = new Drink(COLA, 1.20);
        beer = new Drink(BEER, 2);
        colaCompartment = new Compartment<>(cola, 2);
        beerCompartment = new Compartment<>(beer, 2);
        compartments = new HashMap<>();
        compartments.put(cola.getName(), colaCompartment);
        compartments.put(beer.getName(), beerCompartment);
    }

    /**
     * A user orders a beer and gives exactly three coins (50, 50 and 1 euros).
     * The test checks if he gets exactly one beer and no change, because he gave exactly 2 euros.
     */
    @Test
    public void testReturnDrink() {
        init();
        DrinkMachine drinkMachine = new DrinkMachine(compartments);
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(new Coin(CoinValue.FIFTY_CENT));
        inputCoins.add(new Coin(CoinValue.FIFTY_CENT));
        inputCoins.add(new Coin(CoinValue.ONE_EURO));

        CoinAndQuantity coin_50 = new CoinAndQuantity(CoinValue.FIFTY_CENT, 6);
        CoinAndQuantity coin_20 = new CoinAndQuantity(CoinValue.TWENTY_CENT, 2);
        CoinAndQuantity coin_10 = new CoinAndQuantity(CoinValue.TEN_CENT, 2);
        drinkMachine.addAvailableCoinForChange(coin_10);
        drinkMachine.addAvailableCoinForChange(coin_20);
        drinkMachine.addAvailableCoinForChange(coin_50);

        DrinkAndChange drinkAndChange = drinkMachine.buy(beer, inputCoins);

        assertEquals(beer.getName(), drinkAndChange.getDrinkName());
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
        init();
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(new Coin(CoinValue.TWO_EURO));

        DrinkMachine drinkMachine = new DrinkMachine(compartments);

        /**
         * Set coins for change
         */
        CoinAndQuantity coin_50 = new CoinAndQuantity(CoinValue.FIFTY_CENT, 1);
        CoinAndQuantity coin_20 = new CoinAndQuantity(CoinValue.TWENTY_CENT, 4);
        CoinAndQuantity coin_10 = new CoinAndQuantity(CoinValue.TEN_CENT, 8);
        drinkMachine.addAvailableCoinForChange(coin_10);
        drinkMachine.addAvailableCoinForChange(coin_20);
        drinkMachine.addAvailableCoinForChange(coin_50);

        DrinkAndChange drinkAndChange = drinkMachine.buy(cola, inputCoins);

        assertEquals(cola.getName(), drinkAndChange.getDrinkName());
        assertEquals(1, drinkAndChange.getChange().get(CoinValue.FIFTY_CENT));
        assertEquals(1, drinkAndChange.getChange().get(CoinValue.TWENTY_CENT));
        assertEquals(1, drinkAndChange.getChange().get(CoinValue.TEN_CENT));

        assertEquals(3, drinkMachine.getAvailableCoinForChange().size());

        for (CoinAndQuantity coinAndQuantity : drinkMachine.getAvailableCoinForChange()) {
            assertNotEquals(CoinValue.FIFTY_CENT, coinAndQuantity.getType());
        }
    }

    /**
     * A user orders a cola. As the ordered drink is no longer available,
     * the purchase is not made. This test checks or no cola is given.
     */
    @Test
    public void testNoExistingDrink() {
        init();
        HashMap<String, Compartment> compartments = new HashMap<>();

        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(new Coin(CoinValue.TWO_EURO));

        DrinkMachine drinkMachine = new DrinkMachine(compartments);

        DrinkAndChange drinkAndChange = drinkMachine.buy(cola, inputCoins);
        assertNull(drinkAndChange);
    }

    /**
     * A user orders a cola and gives 2 euros. But a cola costs 1.20 euro.
     * As the machine has no more change he should not place the order.
     */
    @Test
    public void testNotEnoughCoinForDrink() {
        init();
        List<Coin> inputCoins = new ArrayList<>();
        inputCoins.add(new Coin(CoinValue.ONE_EURO));

        DrinkMachine drinkMachine = new DrinkMachine(compartments);

        DrinkAndChange result = drinkMachine.buy(cola, inputCoins);

        assertNull(result);
    }

    /**
     * This test checks the functionality of the method fillInWithCoin,
     * which has the task of refilling change in the machine.
     * The reserve is filled with ten cent and at the end of this operation 2 cent is available for change.
     */
    @Test
    public void testFillInChangeCoins() {
        init();
        List<Coin> inputCoins = new ArrayList<>();

        inputCoins.add(new Coin(CoinValue.TEN_CENT));

        DrinkMachine drinkMachine = new DrinkMachine(compartments);

        assertEquals(0, drinkMachine.getAvailableCoinForChange().size());

        drinkMachine.fillInWithCoin(inputCoins);

        assertEquals(1, drinkMachine.getAvailableCoinForChange().size());

        assertEquals(CoinValue.TEN_CENT, drinkMachine.getAvailableCoinForChange().get(0).getType());
    }
}