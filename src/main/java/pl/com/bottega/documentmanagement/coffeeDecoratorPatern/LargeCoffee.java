package pl.com.bottega.documentmanagement.coffeeDecoratorPatern;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public class LargeCoffee implements Coffee {

    public LargeCoffee (){}

    @Override
    public int cost() {
        return 10;
    }

    @Override
    public String name() {
        return "Large Coffee";
    }
}
