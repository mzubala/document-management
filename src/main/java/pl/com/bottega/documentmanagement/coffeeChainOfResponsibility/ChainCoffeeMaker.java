package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

/**
 * Created by bartosz.paszkowski on 24.08.2016.
 */
public interface ChainCoffeeMaker{


    void setNextChain(ChainCoffeeMaker nextInChain);
    void costCoffee(ChainCoffee request);
    //String nameCoffee(ChainCoffee chainCoffee){}
}
