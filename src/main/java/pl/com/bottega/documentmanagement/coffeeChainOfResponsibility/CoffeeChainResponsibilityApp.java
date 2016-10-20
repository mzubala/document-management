package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

/**
 * Created by bartosz.paszkowski on 24.08.2016.
 */
public class CoffeeChainResponsibilityApp {



    public static void main(String[] args) {

        IngredientPrim c1 = new SmallChainCoffee();
        IngredientPrim c2 = new MediumChainCoffee();
        IngredientPrim c3 = new LargeChainCoffee();
        IngredientPrim c4 = new CoffeeWithSyrup();
        IngredientPrim c5 = new CoffeeWithMilk();
        c1.setNextChain(c2);
        c2.setNextChain(c3);
        c3.setNextChain(c4);
        c4.setNextChain(c5);


        ChainCoffee coffee = new ChainCoffee("small coffee with syrup");
        c1.costCoffee(coffee);
        System.out.println(coffee.getName() + " cost " + coffee.getCost());
    }

}
