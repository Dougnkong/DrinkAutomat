package main.drinkautomat.model;

import main.drinkautomat.common.CoinValue;

/**
 * This class is used to store change coins.
 */
public class CoinAndQuantity implements Comparable<CoinAndQuantity> {

    protected CoinValue type;
    protected int quantity;

    /**
     * @param coin
     * @param quantity gives the number of coins for type coin
     */
    public CoinAndQuantity(CoinValue coin, int quantity) {

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

    public CoinValue getType() {
        return type;
    }

    @Override
    public int compareTo(CoinAndQuantity o) {

        CoinAndQuantity f = (CoinAndQuantity) o;

        return this.type.getValue() - f.type.getValue();
    }
}
