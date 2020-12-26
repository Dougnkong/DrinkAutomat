package com.model;

import com.interfaces_and_enum.AbstractProductType;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "drinks")
public class Drink extends AbstractProductType {
    @Id
    @GeneratedValue(strategy = AUTO)
    protected long id;

    public Drink(long id, String name, double price) {
        super(name, price);
        this.id = id;
    }

    public Drink() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = (int) ((79 * hash) + this.price);
        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("drink{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}