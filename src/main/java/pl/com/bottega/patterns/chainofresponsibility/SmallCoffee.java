package pl.com.bottega.patterns.chainofresponsibility;

import java.math.BigDecimal;

/**
 * Created by anna on 28.08.2016.
 */
public class SmallCoffee extends Ingredient {

    public SmallCoffee(Ingredient next) {
        super(next);
    }

    public SmallCoffee() {

    }

    @Override
    protected BigDecimal ingredientCost() {
        return new BigDecimal(2.5);
    }

    @Override
    protected String ingredientName() {
        return "Small coffee";
    }
}
