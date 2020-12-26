package com.model;

import com.interfaces_and_enum.EnumCoinValue;

public class Coin {

    EnumCoinValue coinValue;

    /**
     * @param coinValue represents the real coin value.
     */
    public Coin(EnumCoinValue coinValue) {
        this.coinValue = coinValue;
    }

    public EnumCoinValue getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(EnumCoinValue coinValue) {
        this.coinValue = coinValue;
    }
}