package drinkAutomat.model;

import drinkAutomat.common.CoinValue;

public class Coin {

    CoinValue coinValue;

    /**
     * @param coinValue represents the real coin value.
     */
    public Coin(CoinValue coinValue) {
        this.coinValue = coinValue;
    }

    public CoinValue getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(CoinValue coinValue) {
        this.coinValue = coinValue;
    }
}
