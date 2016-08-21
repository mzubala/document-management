package pl.com.bottega.documentmanagement.domain.coffee;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-21.
 */
public class MediumCoffee implements Coffee {
    @Override
    public BigDecimal cost() {
        return new BigDecimal(3);
    }

    @Override
    public String name() {
        return "Medium coffee";
    }
}
