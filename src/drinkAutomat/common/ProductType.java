package drinkAutomat.common;

public abstract class ProductType {
    protected String name;
    protected double price;

    /**
     * @param name  general name for drink product.
     * @param price general price of drink.
     */
    public ProductType(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
