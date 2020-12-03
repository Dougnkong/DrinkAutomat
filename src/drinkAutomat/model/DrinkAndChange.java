package drinkAutomat.model;

import drinkAutomat.common.Coin;
import drinkAutomat.common.Drink;

import java.util.HashMap;
import java.util.List;


public class DrinkAndChange {

    HashMap<Coin, Integer> change;
    Drink drink;

    public DrinkAndChange(Drink drink, List<CoinAndQuantity> changes) {
        this.drink = drink;
        this.change = new HashMap<Coin, Integer>();

        for (CoinAndQuantity change : changes) {

            this.change.put(change.type, change.quantity);

        }
    }

    public String getDrinkName() {
        return drink.getName();
    }

    public HashMap<Coin, Integer> getChange() {
        return change;
    }

    public void setChange(HashMap<Coin, Integer> change) {
        this.change = change;
    }
}
