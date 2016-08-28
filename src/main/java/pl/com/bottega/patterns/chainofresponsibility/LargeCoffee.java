package pl.com.bottega.patterns.chainofresponsibility;

import java.math.BigDecimal;

/**
 * Created by anna on 28.08.2016.
 */
public class LargeCoffee extends Ingredient {

    public LargeCoffee(Ingredient next) {
        super(next);
    }

    public LargeCoffee() {

    }

    @Override
    protected BigDecimal ingredientCost() {
        return new BigDecimal(5.0);
    }

    @Override
    protected String ingredientName() {
        return "Large coffee";
    }
}
