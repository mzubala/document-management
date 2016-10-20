package pl.com.bottega.documentmanagement.mars;

import java.util.Scanner;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class MarsRoverApp {



    public static void main(String[] args) {

        //Position position = new Position(0,0);
        MarsRover positionMars = new MarsRover();

        while(true){
            System.out.println("Enter command: (m, rl, rr): ");

            String command = new Scanner(System.in).nextLine();
            if (command.equals("m"))
                positionMars.move();
            else if (command.equals("rl"))
                positionMars.rotateLeft();
            else if (command.equals("rr"))
                positionMars.rotateRight();
            System.out.println("x: " +positionMars.position().getX()+
                    " y: "+ positionMars.position().getY()+ " dir: "+positionMars.getDirection());
            System.out.println();

        }
    }



}
