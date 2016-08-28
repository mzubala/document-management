package pl.com.bottega.coffee_chain;

import java.math.BigDecimal;

/**
 * Created by maciuch on 28.08.16.
 */
public class CoffeeMaker {

    private Ingredient firstIngredient;

    public CoffeeMaker(Ingredient firstIngredient) {
        this.firstIngredient = firstIngredient;
    }

    public BigDecimal cost() {
        return firstIngredient.cost();
    }

    public String name() {
        return firstIngredient.name();
    }

    public static void main(String[] args) {
        CoffeeMaker c1 = new CoffeeMaker(new SmallCoffee(new Milk()));
        CoffeeMaker c2 = new CoffeeMaker(new LargeCoffee());
        CoffeeMaker c3 = new CoffeeMaker(new LargeCoffee(new Milk()));

        System.out.println(c1.name() + ": " + c1.cost());
        System.out.println(c2.name() + ": " + c2.cost());
        System.out.println(c3.name() + ": " + c3.cost());
    }

}
