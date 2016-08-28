package pl.com.bottega.coffee_chain;

import java.math.BigDecimal;

/**
 * Created by maciuch on 28.08.16.
 */
public class SmallCoffee extends Ingredient {

    public SmallCoffee(Ingredient next) {
        super(next);
    }

    @Override
    protected BigDecimal ingredientCost() {
        return new BigDecimal(2.5);
    }

    @Override
    protected String ingredientName() {
        return "Small Coffee";
    }
}
