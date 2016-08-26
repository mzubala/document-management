package pl.com.bottega.mathplay;

import java.util.Collection;
import java.util.Scanner;

/**
 * Created by anna on 25.08.2016.
 */
public abstract class ConsoleApplication {
    protected Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            printMenu();
            System.out.println("Choose option or if you do not want play enter 'quit': ");
            String cmd = getCommand();
            if (cmd.equals("quit"))
                return;
            execute(cmd);
        }
    }

    private void printMenu() {
        Collection<String> menuItems = menuItems();
        System.out.println("Playing with math for high school students");
        for (String item : menuItems)
            System.out.println(item);
    }

    private String getCommand() {
        return scanner.nextLine();
    }

    protected void execute(String cmd) {
        CommandFactory commandFactory = commandFactory();
        Command command = commandFactory.createCommand(cmd);
        command.execute();
    }

    protected abstract pl.com.bottega.mathplay.CommandFactory commandFactory();

    protected abstract Collection<String> menuItems();
}
