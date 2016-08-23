package pl.com.bottega.documentmanagement.coffeeDecoratorPatern;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public class CoffeeConsoleApp {
    public static void main(String[] args) {
        Coffee coffee = new CoffeeWithMilk(new CoffeeWithSyrup(new LargeCoffee()));
        int cost = coffee.cost();
        String name = coffee.name();
        System.out.println(name + " cost " +cost);

    }

}
