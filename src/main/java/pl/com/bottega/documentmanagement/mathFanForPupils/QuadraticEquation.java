package pl.com.bottega.documentmanagement.mathFanForPupils;

import java.util.Scanner;

/**
 * Created by bartosz.paszkowski on 26.08.2016.
 */
public class QuadraticEquation implements Request {

    @Override
    public void execute() {
        System.out.println("Rozwiązywanie równania kwadratowego typu: ax^2 + bx + c");
        System.out.println("Podaj współczynnik a ");
        String a = new Scanner(System.in).nextLine();
        System.out.println("Podaj współczynnik b ");
        String b = new Scanner(System.in).nextLine();
        System.out.println("Podaj wyraz wolny c ");
        String c = new Scanner(System.in).nextLine();

        calculateQuadraticEquation(a, b, c);

    }

    private void calculateQuadraticEquation(String first, String second, String third) {
        int a = Integer.parseInt(first);
        int b = Integer.parseInt(second);
        int c = Integer.parseInt(third);

        double discriminant = b*b - 4*a*c;

        if (discriminant > 0){
            double x1 = ((-b) + Math.sqrt(discriminant))/(2*a);
            double x2 = ((-b) - Math.sqrt(discriminant))/(2*a);
            System.out.println("Delta więksa o zera");
            System.out.println("Mamy dwa rozwiązania:");
            System.out.format("x1 =  %.2f%n", x1);
            System.out.format("x2 =  %.2f%n", x2);
            System.out.println();
        }
        if (discriminant == 0){
            double x = (-b)/(2*a);
            System.out.println("Delta równa zero");
            System.out.println("Mamy jedno rozwiązanie:");
            System.out.format("x =  %.2f%n ", x);
            System.out.println();
        }
        if (discriminant < 0){
            System.out.println("Delta mniejsza od zera");
            System.out.println("Równanie nie posiada rzeczywistych rozwiązń");
            System.out.println();
        }
    }


}
