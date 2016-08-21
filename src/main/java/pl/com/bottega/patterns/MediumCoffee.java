package pl.com.bottega.patterns;

import java.math.BigDecimal;

/**
 * Created by anna on 21.08.2016.
 */
public class MediumCoffee implements Coffee {
    @Override
    public BigDecimal cost() {
        return new BigDecimal(7.5);
    }

    @Override
    public String name() {
        return "Medium Coffee";
    }
}
