package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

/**
 * Created by bartosz.paszkowski on 24.08.2016.
 */
public class CoffeeWithSyrup implements ChainCoffeeMaker {

    private ChainCoffeeMaker chainCoffeeMaker;

    @Override
    public void setNextChain(ChainCoffeeMaker nextInChain) {
        this.chainCoffeeMaker = nextInChain;
    }

    @Override
    public void costCoffee(ChainCoffee request) {
        if (request.getName().contains("syrup")){
            int price = request.getCost();
            request.setCost(price + 3);

        }
        else{
            chainCoffeeMaker.costCoffee(request);
        }
    }
}
