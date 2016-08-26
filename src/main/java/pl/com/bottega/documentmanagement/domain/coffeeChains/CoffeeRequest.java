package pl.com.bottega.documentmanagement.domain.coffeeChains;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Created by Dell on 2016-08-26.
 */
public class CoffeeRequest {

    private Size size;
    private HashSet<Ingredients> ingredients = new LinkedHashSet<>();
    protected int price;
    protected String name;

    public CoffeeRequest(Size size, HashSet<Ingredients> ingedientsAndSize) {
        this.size = size;
        this.ingredients = ingedientsAndSize;
    }

    public HashSet<Ingredients> getIngredients() {
        return ingredients;
    }

    public Size getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
