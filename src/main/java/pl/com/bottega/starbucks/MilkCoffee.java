package pl.com.bottega.starbucks;

import java.math.BigDecimal;

/**
 * Created by maciuch on 21.08.16.
 */
public class MilkCoffee extends CoffeeDecorator {

    MilkCoffee(Coffee coffee) {
        super(coffee);
    }

    @Override
    public BigDecimal cost() {
        return coffee.cost().add(new BigDecimal(1.0));
    }

    @Override
    public String name() {
        return coffee.name() + " with milk";
    }
}
