package pl.com.bottega.documentmanagement.coffeeChainv1;

/**
 * Created by bartosz.paszkowski on 25.08.2016.
 */
public class SmallCoffee2 extends Ingredient {

    private Ingredient ingredient;


    public SmallCoffee2(Ingredient ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public int ingredientCost() {

        return 5;
    }

    @Override
    public String ingredientName() {

        return "Small Coffee";
    }
}
