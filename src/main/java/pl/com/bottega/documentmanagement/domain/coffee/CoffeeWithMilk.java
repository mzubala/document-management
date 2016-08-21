package pl.com.bottega.documentmanagement.domain.coffee;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-21.
 */
public class CoffeeWithMilk extends CoffeeDecorator {

    public CoffeeWithMilk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public BigDecimal cost() {
        return coffee.cost().add(new BigDecimal(0.5));
    }

    @Override
    public String name() {
        return coffee.name() + " with milk";
    }
}
