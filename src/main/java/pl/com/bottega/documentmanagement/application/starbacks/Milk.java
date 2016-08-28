package pl.com.bottega.documentmanagement.application.starbacks;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-28.
 */
public class Milk extends Ingridient {

    public Milk() {}

    public Milk(Ingridient next) {
        super(next);
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
