package pl.com.bottega.mathplay;

import org.springframework.context.ApplicationContext;

/**
 * Created by anna on 25.08.2016.
 */
public class PlayMathCommandFactory implements CommandFactory {

    private ApplicationContext applicationContext;

    public PlayMathCommandFactory(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public Command createCommand(String command) {
        SpringCommand springCommand;
        if (command.equals("1"))
            springCommand = new SolveQuadraticEquation();
        else if (command.equals("2"))
            springCommand = new CalculateSine();
        else if (command.equals("3"))
            springCommand = new CalculateCosine();
        else if (command.equals("4"))
            springCommand = new CalculatePowerOf();
        else springCommand = new WrongCommand();
        springCommand.setApplicationContext(applicationContext);
        return springCommand;
    }
}
