package drinkAutomat.common;

import drinkAutomat.model.CoinAndQuantity;

import java.util.List;

public abstract class Automat {
    ///TODO

    protected abstract void fillInWithCoin(List<Coin> coins);
    //protected  abstract void fillIntWithDrink();
    protected abstract void fillOut(List<CoinAndQuantity> restCoinToGiveBack);
    protected abstract List<CoinAndQuantity> changeCoin(int sum);
}
