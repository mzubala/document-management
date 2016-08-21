package pl.com.bottega.patterns;

/**
 * Created by anna on 21.08.2016.
 */
public class StarbucksOrder {

    public static void main(String[] args){
        Coffee coffee = new SoyMilk(new PeachSyrup((new SmallCoffee())));

        System.out.println(coffee.name() + " - cost: " + coffee.cost());
    }
}
