package pl.com.bottega.documentmanagement.domain.coffeeChains;

import static pl.com.bottega.documentmanagement.domain.coffeeChains.Ingredients.MILK;

/**
 * Created by Dell on 2016-08-26.
 */
public class Milk extends Coffee {

    @Override
    protected void handleRequest(CoffeeRequest request) {
        if (request.getIngredients().contains(MILK)) {
            request.setPrice(request.getPrice() + 1);
            request.setName(request.getName() + " with milk");
        }
        if (nextIngredient != null)
            nextIngredient.handleRequest(request);
    }
}
