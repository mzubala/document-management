package pl.com.bottega.starbucks;

/**
 * Created by maciuch on 21.08.16.
 */
public class StarbucksPointOfSale {

    public static void main(String[] args) {
        Coffee coffe = new Milk(new PeachSyrup(new SmallCoffee()));

        System.out.println(coffe.name() + " - " + coffe.cost());
    }
}
