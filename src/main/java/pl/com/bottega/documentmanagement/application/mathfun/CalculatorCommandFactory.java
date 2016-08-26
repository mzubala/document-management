package pl.com.bottega.documentmanagement.application.mathfun;

import pl.com.bottega.documentmanagement.application.templatemethod.Command;
import pl.com.bottega.documentmanagement.application.templatemethod.CommandFactory;
import pl.com.bottega.documentmanagement.application.templatemethod.UnknownCommand;

/**
 * Created by Dell on 2016-08-26.
 */
public class CalculatorCommandFactory implements CommandFactory {

    @Override
    public Command createCommand(String command) {
        if (command.equals("1"))
            return new QuadraticEquation();
        else if (command.equals("2"))
            return new SineCalculator();
        else if (command.equals("3"))
            return new CosineCalculator();
        else if (command.equals("4"))
            return new PowerOfTwo();
        else
            return new UnknownCommand();
    }
}
