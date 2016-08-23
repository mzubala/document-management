package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.application.Command;
import pl.com.bottega.documentmanagement.application.CommandFactory;

import java.util.Collection;
import java.util.Scanner;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public abstract class ConsoleApplication {

    protected Scanner scanner = new Scanner(System.in);

    public void run(){
        while(true){
            printMenu();
            String cmd = getCommand();
            if (cmd.equals("quit"))
                return;
            execute(cmd);
        }
    }

    protected void execute(String cmd) {
        CommandFactory commandFactory = commandFactory();
        Command command = commandFactory.createCommand(cmd);
        command.execute();
    }

    protected abstract Collection<String> menuItems();
    protected abstract CommandFactory commandFactory();

    private void printMenu() {
        Collection<String> menuItems = menuItems();
        for (String item : menuItems)
            System.out.println(item);
    }

    private String getCommand() {
        return  scanner.nextLine();
    }


}
