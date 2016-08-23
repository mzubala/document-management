package pl.com.bottega.documentmanagement.coffeeDecoratorPatern;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee){
        this.coffee = coffee;
    }
    public abstract int cost();
    public abstract String name();
}
