package pl.com.bottega.mathplay;

import java.util.Scanner;

/**
 * Created by anna on 25.08.2016.
 */
public class SolveQuadraticEquation extends SpringCommand {

    public double calculateDelta(double a, double b, double c) {
        return Math.pow(b, 2) - 4 * a * c;
    }

    public void deltaGreaterThenZero(double a, double b, double c){
        double x1 = (-b - Math.sqrt(calculateDelta(a, b, c))) / (2 * a);
        double x2 = (-b + Math.sqrt(calculateDelta(a, b, c))) / (2 * a);
        System.out.println("When delta is greater then zero, then the roots of the quadratic equation: x1 = " + x1 + " and x2 = " + x2);
    }

    public void deltaEqualsZero(double a, double b) {
        double x0 = - b / (2 * a);
        System.out.println("When delta is equal to zero, then the root of the quadratic equation: x0 = " + x0);
    }

    public void deltaLessThenZero() {
        System.out.println("When delta is less then zero, then equation has no real roots.");
    }

    @Override
    public void execute() {
        System.out.println("Enter a: ");
        double a = Double.valueOf(new Scanner(System.in).nextLine());
        while (a == 0) {
            System.out.println("a must be grater then zero, enter a correct: ");
            a = Double.valueOf(new Scanner(System.in).nextLine());
        }

        System.out.println("Enter b: ");
        double b = Double.valueOf(new Scanner(System.in).nextLine());

        System.out.println("Enter c: ");
        double c = Double.valueOf(new Scanner(System.in).nextLine());

        double delta = calculateDelta(a, b, c);
        System.out.println("Delta is equal to: " + delta);
        if (delta > 0)
            deltaGreaterThenZero(a, b, c);
        if (delta < 0)
            deltaLessThenZero();
        if (delta == 0)
            deltaEqualsZero(a, b);
    }
}
