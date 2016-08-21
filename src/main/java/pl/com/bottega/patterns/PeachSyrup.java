package pl.com.bottega.patterns;

import java.math.BigDecimal;

/**
 * Created by anna on 21.08.2016.
 */
public class PeachSyrup extends CoffeeDecorator{

    PeachSyrup(Coffee coffee) {
        super(coffee);
    }

    @Override
    public BigDecimal cost() {
        return coffee.cost().add(new BigDecimal(2.0));
    }

    @Override
    public String name() {
        return coffee.name() + " with peach syrup";
    }
}
