package drinkAutomat.model;

import drinkAutomat.common.CoinValue;
import drinkAutomat.common.Drink;

import java.util.HashMap;
import java.util.List;

/**
 * This is used to return the drink and the change for the user.
 */
public class DrinkAndChange {

    HashMap<CoinValue, Integer> change;
    Drink drink;

    /**
     * @param drink   ordered.
     * @param changes rest after buying.
     */
    public DrinkAndChange(Drink drink, List<CoinAndQuantity> changes) {
        this.drink = drink;
        this.change = new HashMap<CoinValue, Integer>();

        for (CoinAndQuantity change : changes) {

            this.change.put(change.type, change.quantity);

        }
    }

    public String getDrinkName() {
        return drink.getName();
    }

    public HashMap<CoinValue, Integer> getChange() {
        return change;
    }

    public void setChange(HashMap<CoinValue, Integer> change) {
        this.change = change;
    }
}
