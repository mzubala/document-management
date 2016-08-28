package pl.com.bottega.coffee_chain;

import java.math.BigDecimal;

/**
 * Created by maciuch on 28.08.16.
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
        return "Big Coffee";
    }
}
