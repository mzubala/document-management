package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

/**
 * Created by bartosz.paszkowski on 24.08.2016.
 */
public class MediumChainCoffee implements IngredientPrim {

    private IngredientPrim chainCoffeeMaker;

    @Override
    public void setNextChain(IngredientPrim nextInChain) {
        this.chainCoffeeMaker = nextInChain;
    }

    @Override
    public void costCoffee(ChainCoffee request) {
        if (request.getName().contains("medium")){
            int price = request.getCost();
            request.setCost(price + 7);
        }
        else{
            chainCoffeeMaker.costCoffee(request);
        }
    }
}
