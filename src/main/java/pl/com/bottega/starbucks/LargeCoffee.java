package pl.com.bottega.starbucks;

import java.math.BigDecimal;

/**
 * Created by maciuch on 21.08.16.
 */
public class LargeCoffee implements Coffee {

    @Override
    public BigDecimal cost() {
        return new BigDecimal(7.0);
    }

    @Override
    public String name() {
        return "Large coffee";
    }
}
