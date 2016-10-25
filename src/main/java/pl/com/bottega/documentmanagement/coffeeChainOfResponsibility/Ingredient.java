package pl.com.bottega.documentmanagement.coffeeChainOfResponsibility;

import java.math.BigDecimal;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public abstract class Ingredient {

    private Ingredient next ;

    public Ingredient() {}

    public Ingredient (Ingredient next){
        this.next = next;
    }

    public BigDecimal cost(){
        if (next != null)
            return next.cost().add(ingrediantCost());
        else
            return ingrediantCost();
    }



    public String name(){
        if (next != null)
            return ingredientName() + " with " + next.name();
        else
            return ingredientName();
    }

    protected abstract String ingredientName();
    protected abstract BigDecimal ingrediantCost();
}
