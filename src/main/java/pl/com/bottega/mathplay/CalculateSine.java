package pl.com.bottega.mathplay;

import java.util.Scanner;

/**
 * Created by anna on 25.08.2016.
 */
public class CalculateSine extends SpringCommand {

    private double calculateSine(double angleMeasure) {
        return Math.sin(Math.toRadians(angleMeasure));
    }

    @Override
    public void execute() {
        System.out.println("Enter angle measure: ");
        double angleMeasure = Double.valueOf(new Scanner(System.in).nextLine());
        System.out.println("Sine of " + angleMeasure + " degrees equals " + calculateSine(angleMeasure));
    }
}
