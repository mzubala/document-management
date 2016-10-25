package pl.com.bottega.documentmanagement.coffeeChainv1;

/**
 * Created by bartosz.paszkowski on 24.08.2016.
 */
public class ChainCoffee {

    private String name;
    private int cost;

    public ChainCoffee(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
