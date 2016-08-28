package pl.com.bottega.documentmanagement.application.starbacks;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-28.
 */
public class SmallCoffee extends Ingridient {

    public SmallCoffee(){}

    public SmallCoffee(Ingridient next) {
        super(next);
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
