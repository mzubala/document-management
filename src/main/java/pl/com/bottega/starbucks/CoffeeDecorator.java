package pl.com.bottega.starbucks;

/**
 * Created by maciuch on 21.08.16.
 */
public abstract class CoffeeDecorator implements Coffee {

    protected Coffee coffee;

    CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}
