package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

/**
 * Created by bartosz.paszkowski on 24.08.2016.
 */
public class CoffeeChainResponsibilityApp {



    public static void main(String[] args) {

        ChainCoffeeMaker c1 = new SmallChainCoffee();
        ChainCoffeeMaker c2 = new MediumChainCoffee();
        ChainCoffeeMaker c3 = new LargeChainCoffee();
        ChainCoffeeMaker c4 = new SmallChainCoffee();
        ChainCoffeeMaker c5 = new CoffeeWithSyrup();
        ChainCoffeeMaker c6 = new CoffeeWithMilk();
        c1.setNextChain(c2);
        c2.setNextChain(c3);
        c3.setNextChain(c4);
        c4.setNextChain(c5);
        c5.setNextChain(c6);

        ChainCoffee request = new ChainCoffee("large coffee with syrup");
        c1.costCoffee(request);
        System.out.println(request.getName() + " cost " + request.getCost());
    }

}
