package pl.com.bottega.starbucks;

import java.math.BigDecimal;

/**
 * Created by maciuch on 21.08.16.
 */
public class SmallCoffee implements Coffee {

    @Override
    public BigDecimal cost() {
        return new BigDecimal(5.0);
    }

    @Override
    public String name() {
        return "Small coffee";
    }
}
