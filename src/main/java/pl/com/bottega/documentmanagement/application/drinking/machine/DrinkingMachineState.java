package pl.com.bottega.documentmanagement.application.drinking.machine;

/**
 * Created by Dell on 2016-08-28.
 */
public abstract class DrinkingMachineState {

    private DrinkingMachine drinkingMachine;

    public DrinkingMachineState(DrinkingMachine drinkingMachine) {
        this.drinkingMachine = drinkingMachine;
    }

    public abstract void insertedCoin(int money);
    public abstract void selectDrink(Drink drink);
    public abstract void cancel();
}
