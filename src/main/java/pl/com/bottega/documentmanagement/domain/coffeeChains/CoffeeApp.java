package pl.com.bottega.documentmanagement.domain.coffeechains;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Dell on 2016-08-26.
 */
public class CoffeeApp {

    public static void main(String[] args) {
        Coffee coffee = setUpChain();

        CoffeeRequest request = new CoffeeRequest(Size.SMALL, new HashSet<>(Arrays.asList(Ingredients.MILK, Ingredients.SYRUP)));
        coffee.handleRequest(request);
        System.out.println(request.getName());
        System.out.println(request.getPrice());
    }

    public static Coffee setUpChain() {
        Coffee smallSize = new SmallCoffee();
        Coffee mediumSize = new MediumCoffee();
        Coffee largeSize = new LargeCoffee();

        Coffee milk = new Milk();
        Coffee syrup = new Syrup();

        smallSize.setNextIngredient(mediumSize);
        mediumSize.setNextIngredient(largeSize);
        largeSize.setNextIngredient(milk);
        milk.setNextIngredient(syrup);

        return smallSize;
    }
}
