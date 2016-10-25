package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

import java.math.BigDecimal;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class LargeCoffee extends Ingredient {
    public LargeCoffee(Ingredient next) {
        super(next);
    }

    public LargeCoffee() {

    }

    @Override
    protected String ingredientName() {
        return "Big Coffee";
    }

    @Override
    protected BigDecimal ingrediantCost() {
        return new BigDecimal(5.0);
    }
}
