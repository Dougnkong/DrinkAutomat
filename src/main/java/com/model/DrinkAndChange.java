package com.model;

import com.interfaces_and_enum.EnumCoinValue;

import java.util.HashMap;
import java.util.List;

/**
 * This is used to return the drink and the change for the com.user.
 */
public class DrinkAndChange {

    HashMap<EnumCoinValue, Integer> change;
    Drink drink;

    /**
     * @param drink   ordered.
     * @param changes rest after buying.
     */
    public DrinkAndChange(Drink drink, List<CoinAndQuantity> changes) {
        this.drink = drink;
        this.change = new HashMap<EnumCoinValue, Integer>();

        for (CoinAndQuantity change : changes) {

            this.change.put(change.type, change.quantity);

        }
    }

    public String getDrinkName() {
        return drink.getName();
    }

    public HashMap<EnumCoinValue, Integer> getChange() {
        return change;
    }

    public void setChange(HashMap<EnumCoinValue, Integer> change) {
        this.change = change;
    }
}