package pl.com.bottega.documentmanagement.application.drinking.machine;

/**
 * Created by Dell on 2016-08-28.
 */
public class ReadyState extends DrinkingMachineState {


    public ReadyState(DrinkingMachine drinkingMachine) {
        super(drinkingMachine);
    }

    @Override
    public void insertedCoin(int money) {

    }

    @Override
    public void selectDrink(Drink drink) {

    }

    @Override
    public void cancel() {

    }
}
