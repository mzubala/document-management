package pl.com.bottega.documentmanagement.application.starbacks;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-28.
 */
public class LargeCoffee extends Ingridient {

    public LargeCoffee() {}

    public LargeCoffee(Ingridient next) {
        super(next);
    }

    @Override
    protected BigDecimal ingredientCost() {
        return new BigDecimal(5);
    }

    @Override
    protected String ingredientName() {
        return "Big coffee";
    }
}
