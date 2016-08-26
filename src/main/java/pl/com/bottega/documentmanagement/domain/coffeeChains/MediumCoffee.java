package pl.com.bottega.documentmanagement.domain.coffeeChains;

/**
 * Created by Dell on 2016-08-26.
 */
public class MediumCoffee extends Coffee {

    @Override
    protected void handleRequest(CoffeeRequest request) {
        if (request.getSize().equals(Size.MEDIUM)) {
            request.setPrice(4);
            request.setName("Medium coffee");
            nextIngredient.handleRequest(request);
        }
        if (nextIngredient != null)
            nextIngredient.handleRequest(request);
    }
}
