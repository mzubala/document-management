package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

import java.math.BigDecimal;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class SmalCoffee extends Ingredient {


    public SmalCoffee(Ingredient next) {
        super(next);
    }

    @Override
    protected String ingredientName() {
        return "Small Coffee";
    }

    @Override
    protected BigDecimal ingrediantCost() {
        return new BigDecimal(2.5);
    }
}
