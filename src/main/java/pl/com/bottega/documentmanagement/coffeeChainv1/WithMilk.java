package pl.com.bottega.documentmanagement.coffeeChainv1;

/**
 * Created by bartosz.paszkowski on 25.08.2016.
 */
public class WithMilk extends Ingredient {

    private Ingredient ingredient;

    public WithMilk(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int ingredientCost() {

        return 2;
    }

    @Override
    public String ingredientName() {

        return "With Milk";
    }
}
