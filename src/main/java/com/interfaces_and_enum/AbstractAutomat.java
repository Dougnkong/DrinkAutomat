package com.interfaces_and_enum;

import com.model.Coin;
import com.model.CoinAndQuantity;

import java.util.List;

public abstract class AbstractAutomat {

    protected abstract void fillInWithCoin(List<Coin> coins);

    /**
     * TODO
     * Implement: protected  abstract void fillIntWithDrink();
     */
    protected abstract void fillOut(List<CoinAndQuantity> restCoinToGiveBack);

    protected abstract List<CoinAndQuantity> changeCoin(int sum);
}