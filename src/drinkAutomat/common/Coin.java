package drinkAutomat.common;

public enum Coin {
    ONE_EURO(100),
    TWO_EURO(200),
    FIFTY_CENT(50),
    TEN_CENT(10),
    TWENTY_CENT(20);

    private final int coinType;

    Coin(int value) {
        this.coinType = value;
    }

    public int getCoinType() {
        return this.coinType;
    }

}
