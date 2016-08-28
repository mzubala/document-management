package pl.com.bottega.coffee_chain;

import java.math.BigDecimal;

/**
 * Created by maciuch on 28.08.16.
 */
public class Milk extends Ingredient {

    public Milk(Ingredient next) {
        super(next);
    }

    public Milk() {}

    @Override
    protected BigDecimal ingredientCost() {
        return new BigDecimal(0.5);
    }

    @Override
    protected String ingredientName() {
        return "milk";
    }
}
