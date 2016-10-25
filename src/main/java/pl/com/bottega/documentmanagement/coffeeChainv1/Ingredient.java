package pl.com.bottega.documentmanagement.coffeeChainv1;

/**
 * Created by bartosz.paszkowski on 25.08.2016.
 */
public abstract class Ingredient implements Coffee2{

    protected Coffee2 coffee2;
    protected int price;
    private int name;

    public Ingredient() {
        this.price = ingredientCost();
    }

    public int cost(){
        int ing = ingredientCost();
        price = price + ing;
        return price;
    }

    public String name(){

        return null;
    }

    public abstract int ingredientCost();
    public abstract String ingredientName();

}
