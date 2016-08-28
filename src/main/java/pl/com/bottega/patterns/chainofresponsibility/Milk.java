package pl.com.bottega.patterns.chainofresponsibility;

import java.math.BigDecimal;

/**
 * Created by anna on 28.08.2016.
 */
public class Milk extends Ingredient {

    public Milk(Ingredient next) {
        super(next);
    }

    public Milk() {

    }

    @Override
    protected BigDecimal ingredientCost() {
        return new BigDecimal(0.5);
    }

    @Override
    protected String ingredientName() {
        return "milk";
    }
}
