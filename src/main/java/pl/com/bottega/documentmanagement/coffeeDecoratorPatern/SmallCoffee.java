package pl.com.bottega.documentmanagement.coffeeDecoratorPatern;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public class SmallCoffee implements Coffee{

    public SmallCoffee(){}

    @Override
    public int cost() {
        return 5;
    }

    @Override
    public String name() {
        return "Small Coffee";
    }
}
