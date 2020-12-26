package com.interfaces_and_enum;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

import static javax.persistence.GenerationType.*;

public abstract class AbstractProductType {
    protected String name;
    protected double price;

    /**
     * @param name  general name for drink product.
     * @param price general price of drink.
     */
    public AbstractProductType(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public AbstractProductType() {
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