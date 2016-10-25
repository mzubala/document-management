package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

import java.math.BigDecimal;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class CoffeeMaker {

    private Ingredient firstIngredient;

    public CoffeeMaker(Ingredient ingredient){
        this.firstIngredient = ingredient;
    }

    public BigDecimal cost(){
        return firstIngredient.cost();
    }
    public String name(){
        return firstIngredient.name();
    }

    public static void main(String[] args) {
        CoffeeMaker c1 = new CoffeeMaker(new SmalCoffee(new Milk()));
        CoffeeMaker c2 = new CoffeeMaker(new LargeCoffee(new Milk()));
        CoffeeMaker c3 = new CoffeeMaker(new LargeCoffee());

        System.out.println(c1.name() + ": " + c1.cost());
        System.out.println(c2.name() + ": " + c2.cost());
        System.out.println(c3.name() + ": " + c3.cost());
    }
}
