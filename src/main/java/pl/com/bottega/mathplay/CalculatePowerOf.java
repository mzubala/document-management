package pl.com.bottega.mathplay;

import java.util.Scanner;

/**
 * Created by anna on 25.08.2016.
 */
public class CalculatePowerOf extends SpringCommand {

    private double calculatePowerOf(double x) {
        return Math.pow(2,x);
    }

    @Override
    public void execute() {
        System.out.println("Enter exponent: ");
        double x = Double.valueOf(new Scanner(System.in).nextLine());
        System.out.println("2 to the power of " + x + " equals " + calculatePowerOf(x));
    }
}
