package pl.com.bottega.mathplay;

import java.util.Scanner;

/**
 * Created by anna on 25.08.2016.
 */
public class CalculateCosine extends SpringCommand {

    private double calculateCosine(double angleMeasure) {
        return Math.cos(Math.toRadians(angleMeasure));
    }

    @Override
    public void execute() {
        System.out.println("Enter angle measure: ");
        double angleMeasure = Double.valueOf(new Scanner(System.in).nextLine());
        System.out.println("Cosine of " + angleMeasure + " degrees equals " + calculateCosine(angleMeasure));
    }
}
