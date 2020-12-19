package common;

import model.Coin;
import model.CoinAndQuantity;

import java.util.List;

public abstract class Automat {

    protected abstract void fillInWithCoin(List<Coin> coins);

    /**
     * TODO
     * Implement: protected  abstract void fillIntWithDrink();
     */
    protected abstract void fillOut(List<CoinAndQuantity> restCoinToGiveBack);

    protected abstract List<CoinAndQuantity> changeCoin(int sum);
}
