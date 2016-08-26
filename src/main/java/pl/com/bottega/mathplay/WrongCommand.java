package pl.com.bottega.mathplay;

/**
 * Created by anna on 25.08.2016.
 */
public class WrongCommand extends SpringCommand {
    @Override
    public void execute() {
        System.out.println("Sorry, this is not correct command");
    }
}
