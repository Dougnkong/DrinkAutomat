package main.drinkautomat.common;

/**
 * The coin is initialised in cents.
 */
public enum CoinValue {
    ONE_EURO(100),
    TWO_EURO(200),
    FIFTY_CENT(50),
    TEN_CENT(10),
    TWENTY_CENT(20);

    private final int coinValue;

    CoinValue(int value) {
        this.coinValue = value;
    }

    public int getValue() {
        return this.coinValue;
    }

}
