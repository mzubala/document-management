package pl.com.bottega.documentmanagement.domain.coffee;

/**
 * Created by Dell on 2016-08-21.
 */
public class CoffeeAppTest {
    public static void main(String[] args) {
        Coffee coffee = new CoffeeWithSyrop(new CoffeeWithMilk(new SmallCoffee()));
        System.out.println(coffee.name());
        System.out.println(coffee.cost());


        Coffee largeCoffee = new LargeCoffee();
        largeCoffee = new CoffeeWithMilk(largeCoffee);

//        largeCoffee = new CoffeeWithSyrop(largeCoffee);
        System.out.println(largeCoffee.name());
        System.out.println(largeCoffee.cost());
    }
}
