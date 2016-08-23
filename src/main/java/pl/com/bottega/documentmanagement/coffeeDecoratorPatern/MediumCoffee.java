package pl.com.bottega.documentmanagement.coffeeDecoratorPatern;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public class MediumCoffee implements Coffee{

    public MediumCoffee (){}

    @Override
    public int cost() {
        return 8;
    }

    @Override
    public String name() {
        return "Medium Coffee";
    }
}
