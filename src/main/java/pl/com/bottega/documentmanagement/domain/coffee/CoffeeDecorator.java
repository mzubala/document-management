package pl.com.bottega.documentmanagement.domain.coffee;


import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-21.
 */
public abstract class CoffeeDecorator implements Coffee {

    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    public abstract BigDecimal cost();
    public abstract String name();
}
