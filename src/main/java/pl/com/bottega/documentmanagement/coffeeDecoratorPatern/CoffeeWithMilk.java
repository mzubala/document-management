package pl.com.bottega.documentmanagement.coffeeDecoratorPatern;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public class CoffeeWithMilk extends CoffeeDecorator {


    public CoffeeWithMilk(Coffee coffee) {
        super(coffee);
    }


    @Override
    public int cost() {
        return  3 + coffee.cost();
    }

    @Override
    public String name() {
        return coffee.name() + " With Milk";
    }
}
