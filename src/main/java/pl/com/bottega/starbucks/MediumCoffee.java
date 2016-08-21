package pl.com.bottega.starbucks;

import pl.com.bottega.documentmanagement.infrastructure.ColorPrintCostCalculator;

import java.math.BigDecimal;

/**
 * Created by maciuch on 21.08.16.
 */
public class MediumCoffee implements Coffee {

    @Override
    public BigDecimal cost() {
        return new BigDecimal(6.0);
    }

    @Override
    public String name() {
        return "Medium coffee";
    }
}
