package pl.com.bottega.starbucks;

/**
 * Created by maciuch on 21.08.16.
 */
public class StarbucksPointOfSale {

    public static void main(String[] args) {
        Coffee coffee1 = new MilkCoffee(new PeachSyrupCoffee(new SmallCoffee()));
        Coffee coffee2 = new PeachSyrupCoffee(new LargeCoffee());
        Coffee coffee3 = new LargeCoffee();
        coffee3 = new MilkCoffee(coffee3);
        System.out.println(coffee1.name() + " - " + coffee1.cost());
        System.out.println(coffee2.name() + " - " + coffee2.cost());
        System.out.println(coffee3.name() + " - " + coffee3.cost());
    }
}
