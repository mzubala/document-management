package pl.com.bottega.documentmanagement.domain.coffeechains;

import static pl.com.bottega.documentmanagement.domain.coffeechains.Size.SMALL;

/**
 * Created by Dell on 2016-08-26.
 */
public class SmallCoffee extends Coffee {

    @Override
    protected void handleRequest(CoffeeRequest request) {
        if (request.getSize().equals(SMALL)) {
            request.setPrice(3);
            request.setName("Small coffee");
        }
        if (nextIngredient != null)
            nextIngredient.handleRequest(request);
    }
}
