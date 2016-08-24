package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

/**
 * Created by bartosz.paszkowski on 24.08.2016.
 */
public class LargeChainCoffee implements ChainCoffeeMaker {

    private ChainCoffeeMaker chainCoffeeMaker;

    @Override
    public void setNextChain(ChainCoffeeMaker nextInChain) {
        this.chainCoffeeMaker = nextInChain;
    }

    @Override
    public void costCoffee(ChainCoffee request) {
        if (request.getName().contains("large")){
            int price = request.getCost();
            request.setCost(price + 9);
            chainCoffeeMaker.costCoffee(request);
        }
        else{
            chainCoffeeMaker.costCoffee(request);
        }
    }
}
