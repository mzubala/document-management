package pl.com.bottega.documentmanagement.application.mathfun;

import pl.com.bottega.documentmanagement.application.templatemethod.Command;

import java.util.Scanner;

/**
 * Created by Dell on 2016-08-26.
 */
public class PowerOfTwo implements Command {

    @Override
    public void execute() {
        System.out.println("Enter exponent: ");
        int x = Integer.valueOf(new Scanner(System.in).nextLine());
        System.out.println("The result is: " + Math.pow(2, x) + "\n");
    }
}
