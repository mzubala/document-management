package pl.com.bottega.documentmanagement.coffeeChainv1;

/**
 * Created by bartosz.paszkowski on 24.08.2016.
 */
public interface IngredientPrim {


    void setNextChain(IngredientPrim nextInChain);
    void costCoffee(ChainCoffee request);
    //String nameCoffee(ChainCoffee chainCoffee){}
}
