package pl.com.bottega.documentmanagement.domain.coffee;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-21.
 */
public class SmallCoffee implements Coffee {

    @Override
    public BigDecimal cost() {
        return new BigDecimal(2.5);
    }

    @Override
    public String name() {
        return "Small Coffee";
    }
}
