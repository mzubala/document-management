package pl.com.bottega.patterns;

import java.math.BigDecimal;

/**
 * Created by anna on 21.08.2016.
 */
public class LargeCoffee implements Coffee {
    @Override
    public BigDecimal cost() {
        return new BigDecimal(10.0);
    }

    @Override
    public String name() {
        return "Large Coffee";
    }
}
