package pl.com.bottega.patterns;

/**
 * Created by anna on 21.08.2016.
 */
public abstract class CoffeeDecorator implements Coffee {

    protected Coffee coffee;

    CoffeeDecorator(Coffee coffee){
        this.coffee = coffee;
    }
}
