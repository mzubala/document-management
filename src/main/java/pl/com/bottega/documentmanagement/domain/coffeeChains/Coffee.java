package pl.com.bottega.documentmanagement.domain.coffeeChains;

/**
 * Created by Dell on 2016-08-26.
 */
public abstract class Coffee {

    protected Coffee nextIngredient;

    public void setNextIngredient(Coffee nextIngredient) {
        this.nextIngredient = nextIngredient;
    }

    abstract protected void handleRequest(CoffeeRequest request);
}
