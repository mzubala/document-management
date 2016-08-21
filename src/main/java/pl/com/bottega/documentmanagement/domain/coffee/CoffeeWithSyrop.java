package pl.com.bottega.documentmanagement.domain.coffee;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-21.
 */
public class CoffeeWithSyrop extends CoffeeDecorator {

    public CoffeeWithSyrop(Coffee coffee) {
        super(coffee);
    }

    @Override
    public BigDecimal cost() {
        return coffee.cost().add(new BigDecimal(1));
    }

    @Override
    public String name() {
        return coffee.name() + " with syrop";
    }
}
