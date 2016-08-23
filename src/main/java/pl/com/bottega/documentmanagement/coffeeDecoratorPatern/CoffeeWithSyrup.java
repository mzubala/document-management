package pl.com.bottega.documentmanagement.coffeeDecoratorPatern;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public class CoffeeWithSyrup extends CoffeeDecorator {



    public CoffeeWithSyrup(Coffee coffee) {
        super(coffee);
    }


    @Override
    public int cost() {
        return  4 + coffee.cost();
    }

    @Override
    public String name() {
        return coffee.name() + " With Syrup";
    }
}
