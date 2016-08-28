package pl.com.bottega.documentmanagement.application.starbacks;

import java.math.BigDecimal;

/**
 * Created by Dell on 2016-08-28.
 */
public abstract class Ingridient {

    private Ingridient next;

    public Ingridient(){}

    public Ingridient(Ingridient next) {
        this.next = next;
    }

    public BigDecimal cost() {
        if (next != null)
            return next.cost().add(ingredientCost());
        else
            return ingredientCost();
    }

    protected abstract BigDecimal ingredientCost();

    public String name() {
        if (next != null)
            return ingredientName() + " with " + next.name();
        else
            return ingredientName();
    }

    protected abstract String ingredientName();
}
