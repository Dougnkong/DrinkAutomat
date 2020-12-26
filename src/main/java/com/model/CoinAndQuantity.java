package com.model;

import com.interfaces_and_enum.EnumCoinValue;

/**
 * This class is used to store change coins.
 */
public class CoinAndQuantity implements Comparable<CoinAndQuantity> {

    protected EnumCoinValue type;
    protected int quantity;

    /**
     * @param coin
     * @param quantity gives the number of coins for type coin
     */
    public CoinAndQuantity(EnumCoinValue coin, int quantity) {

        this.type = coin;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void reduceCoinNumber(int quantity) {
        this.quantity -= quantity;
    }

    public EnumCoinValue getType() {
        return type;
    }

    @Override
    public int compareTo(CoinAndQuantity o) {

        CoinAndQuantity f = (CoinAndQuantity) o;

        return this.type.getValue() - f.type.getValue();
    }
}