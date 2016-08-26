package pl.com.bottega.documentmanagement.domain.coffeechains;

import static pl.com.bottega.documentmanagement.domain.coffeechains.Ingredients.SYRUP;

/**
 * Created by Dell on 2016-08-26.
 */
public class Syrup extends Coffee {

    @Override
    protected void handleRequest(CoffeeRequest request) {
        if (request.getIngredients().contains(SYRUP)) {
            request.setPrice(request.getPrice() + 2);
            request.setName(request.getName() + " with syrup");
        }
        if (nextIngredient != null)
            nextIngredient.handleRequest(request);
    }
}
