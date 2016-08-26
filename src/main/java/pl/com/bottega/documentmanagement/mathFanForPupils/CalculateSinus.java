package pl.com.bottega.documentmanagement.mathFanForPupils;

import java.util.Scanner;

/**
 * Created by bartosz.paszkowski on 26.08.2016.
 */
public class CalculateSinus implements Request {
    @Override
    public void execute() {
        System.out.println("Podaj kąt w stopniach");
        double degrees = new Scanner(System.in).nextDouble();
        sineX(degrees);
    }
    private void sineX(double degrees) {
        double sin = Math.sin(Math.toRadians(degrees));
        System.out.format("Sinus kąta %.1f to %.4f%n", degrees, sin);
        System.out.println();
    }
}
