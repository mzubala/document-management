package pl.com.bottega.documentmanagement.application.mathfun;

import pl.com.bottega.documentmanagement.application.templatemethod.Command;

import java.util.Scanner;

import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

/**
 * Created by Dell on 2016-08-26.
 */
public class CosineCalculator implements Command {

    @Override
    public void execute() {
        System.out.println("Enter angle: ");
        double degrees = Double.valueOf(new Scanner(System.in).nextLine());
        System.out.format("The cosine of %.1f degrees is %.4f%n\n", degrees, cos(toRadians(degrees)));
    }
}
