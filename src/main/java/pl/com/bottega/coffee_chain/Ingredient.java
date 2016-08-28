package pl.com.bottega.coffee_chain;

import java.math.BigDecimal;

/**
 * Created by maciuch on 28.08.16.
 */
public abstract class Ingredient {

    private Ingredient next;

    public Ingredient() {};

    public Ingredient(Ingredient next) {
        this.next = next;
    }

    public BigDecimal cost() {
        if(next != null)
            return next.cost().add(ingredientCost());
        else
            return ingredientCost();
    }

    public String name() {
        if(next != null)
            return ingredientName() + " with " + next.name();
        else
            return ingredientName();
    }

    protected abstract BigDecimal ingredientCost();

    protected abstract String ingredientName();

}
