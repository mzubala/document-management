package pl.com.bottega.documentmanagement.application;

import java.util.Collection;
import java.util.Scanner;

/**
 * Created by maciuch on 21.08.16.
 */
public abstract class ConsoleApplication {

    protected Scanner scanner = new Scanner(System.in);

    public void run() {
        while(true) {
            printMenu();
            String cmd = getCommand();
            if(cmd.equals("quit"))
                return;
            execute(cmd);
        }
    }

    private void printMenu() {
        Collection<String> menuItems = menuItems();
        for(String item : menuItems)
            System.out.println(item);
    }

    public String getCommand() {
        return scanner.nextLine();
    }

    private void execute(String cmd) {
        CommandFactory commandFacotry = commandFactory();
        Command command = commandFacotry.createCommand(cmd);
        command.execute();
    }

    protected abstract CommandFactory commandFactory();

    protected abstract Collection<String> menuItems();
}
