package pl.com.bottega.documentmanagement.application;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public class UnknownCommand extends SpringCommand {
    @Override
    public void execute() {
        System.out.println("Wrong command :(");
    }
}
