package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

import java.math.BigDecimal;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class Milk extends Ingredient{
    public Milk(Ingredient next) {
        super(next);
    }

    public Milk() {

    }

    @Override
    protected String ingredientName() {
        return "Milk";
    }

    @Override
    protected BigDecimal ingrediantCost() {
        return new BigDecimal(2.0);
    }
}
