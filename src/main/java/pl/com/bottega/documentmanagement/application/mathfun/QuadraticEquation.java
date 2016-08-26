package pl.com.bottega.documentmanagement.application.mathfun;

import pl.com.bottega.documentmanagement.application.templatemethod.Command;

import java.util.Scanner;

/**
 * Created by Dell on 2016-08-26.
 */
public class QuadraticEquation implements Command {

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a value for A: ");
        double a = readLine(scanner);
        System.out.println("Enter a value for B: ");
        double b = readLine(scanner);
        System.out.println("Enter a value for C: ");
        double c = readLine(scanner);

        if ((b*b - 4*a*c)>=0)
        {
            double sol1 = (-b + Math.sqrt(b*b - 4*a*c) ) / (2*a);
            double sol2 = (-b - Math.sqrt(b*b - 4*a*c) ) / (2*a);
            System.out.println("Reasult is: " + sol1 + " " + sol2);
        }
        else
        {
            double sola = (-b / (2*a));
            double solb = Math.sqrt(-(b*b - 4*a*c))/ (2*a);
            System.out.println(sola + " + " + solb + "i and" + sola + " - " + solb +"i");
        }
    }

    private Double readLine(Scanner scanner) {
        return Double.valueOf(scanner.nextLine());
    }
}
