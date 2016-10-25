package pl.com.bottega.documentmanagement.coffeeChainv1;

/**
 * Created by bartosz.paszkowski on 25.08.2016.
 */
public class WithSyrup extends Ingredient {

    private Ingredient ingredient;



    @Override
    public int ingredientCost() {

        return 3;
    }

    @Override
    public String ingredientName() {

        return "With Syrup";
    }
}
