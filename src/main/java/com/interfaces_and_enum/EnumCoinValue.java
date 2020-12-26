package com.interfaces_and_enum;

/**
 * The coin is initialised in cents.
 */
public enum EnumCoinValue {
    ONE_EURO(100),
    TWO_EURO(200),
    FIFTY_CENT(50),
    TEN_CENT(10),
    TWENTY_CENT(20);

    private final int coinValue;

    public int getValue() {
        return this.coinValue;
    }

    EnumCoinValue(int value) {
        this.coinValue = value;
    }
}