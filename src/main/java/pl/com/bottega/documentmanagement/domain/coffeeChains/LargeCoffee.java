package pl.com.bottega.documentmanagement.domain.coffeeChains;

import static pl.com.bottega.documentmanagement.domain.coffeeChains.Size.MEDIUM;

/**
 * Created by Dell on 2016-08-26.
 */
public class LargeCoffee extends Coffee {

    @Override
    protected void handleRequest(CoffeeRequest request) {
        if (request.getSize().equals(MEDIUM)) {
            request.setPrice(5);
            request.setName("Large coffee");
        }
        if (nextIngredient != null)
            nextIngredient.handleRequest(request);
    }
}
