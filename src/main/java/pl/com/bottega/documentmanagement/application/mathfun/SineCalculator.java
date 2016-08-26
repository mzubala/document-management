package pl.com.bottega.documentmanagement.application.mathfun;

import pl.com.bottega.documentmanagement.application.templatemethod.Command;

import java.util.Scanner;

import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Created by Dell on 2016-08-26.
 */
public class SineCalculator implements Command {

    @Override
    public void execute() {
        System.out.println("Enter angle: ");
        double degrees = Double.valueOf(new Scanner(System.in).nextLine());
        System.out.format("The sine of %.1f degrees is %.4f%n\n", degrees, sin(toRadians(degrees)));
    }
}
