package drinkAutomat.model;

import drinkAutomat.common.ProductType;

public class Compartment<T extends ProductType> {

    private T common;
    int quantity;

    public Compartment(T common, int quantity) {
        this.common = common;
        this.quantity = quantity;
    }

    public T getCommon() {
        return common;
    }

    public void setCommon(T common) {
        this.common = common;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
