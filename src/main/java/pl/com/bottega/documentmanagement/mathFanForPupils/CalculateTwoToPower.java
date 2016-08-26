package pl.com.bottega.documentmanagement.mathFanForPupils;

import java.util.Scanner;

/**
 * Created by bartosz.paszkowski on 26.08.2016.
 */
public class CalculateTwoToPower implements Request {
    @Override
    public void execute() {
        System.out.println("Podaj wykładnik potęgi");
        double exponent = new Scanner(System.in).nextDouble();

        exponentiation(exponent);
    }

    private void exponentiation(double expo) {
        double power = Math.pow(2, expo);
        System.out.printf("2 do potęgi wynosi %.0f%n", power);
        System.out.println();

    }


}
