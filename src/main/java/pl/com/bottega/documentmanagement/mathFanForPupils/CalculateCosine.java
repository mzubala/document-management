package pl.com.bottega.documentmanagement.mathFanForPupils;

import java.util.Scanner;

/**
 * Created by bartosz.paszkowski on 26.08.2016.
 */
public class CalculateCosine implements Request {
    @Override
    public void execute() {
        System.out.println("Podaj kąt w stopniach");
        double degrees = new Scanner(System.in).nextDouble();
        cosineX(degrees);
    }

    private void cosineX(double degrees) {
        double cos = Math.cos(Math.toRadians(degrees));
        System.out.format("Cosinus kąta %.1f to %.4f%n", degrees, cos);
        System.out.println();
    }
}
