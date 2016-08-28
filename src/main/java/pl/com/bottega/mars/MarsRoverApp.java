package pl.com.bottega.mars;

import java.util.Scanner;

/**
 * Created by maciuch on 28.08.16.
 */
public class MarsRoverApp {

    public static void main(String[] args) {
        MarsRover marsRover = new MarsRover();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: (m, rl, rr): ");
            String cmd = scanner.next();
            if (cmd.equals("m"))
                marsRover.move();
            else if (cmd.equals("rl"))
                marsRover.rotateLeft();
            else if (cmd.equals("rr"))
                marsRover.rotateRight();
            else
                System.out.println("Sorry I don't understand ;(");
            String msg = String.format("My position is: %s, %s", marsRover.position(), marsRover.getDirection());
            System.out.println(msg);
        }
    }

}
