package pl.com.bottega.documentmanagement.application.starbacks;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-28.
 */
public class CoffeeMaker {

    private Ingridient firstIngridient;

    public CoffeeMaker(Ingridient firstIngridient1) {
        this.firstIngridient = firstIngridient1;
    }

    public BigDecimal cost() {
        return firstIngridient.cost();
    }

    public String name() {
        return firstIngridient.name();
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
