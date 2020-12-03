package drinkAutomat.model;

import drinkAutomat.common.Coin;

public class CoinAndQuantity implements Comparable<CoinAndQuantity> {

    protected Coin type;
    protected int quantity;

    public CoinAndQuantity(Coin coin, int quantity) {

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

    public Coin getType() {
        return type;
    }

    @Override
    public int compareTo(CoinAndQuantity o) {

        CoinAndQuantity f = (CoinAndQuantity) o;

        return this.type.getCoinType() - f.type.getCoinType();
    }
}
