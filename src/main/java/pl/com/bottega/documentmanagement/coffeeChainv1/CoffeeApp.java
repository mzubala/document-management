package pl.com.bottega.documentmanagement.coffeeChainv1;

/**
 * Created by bartosz.paszkowski on 25.08.2016.
 */
public class CoffeeApp {

    public static void main(String[] args) {

        Coffee2 coffee = new SmallCoffee2(new WithMilk(new WithSyrup()));
        int cost = coffee.cost();
        System.out.println(cost);
    }


}
