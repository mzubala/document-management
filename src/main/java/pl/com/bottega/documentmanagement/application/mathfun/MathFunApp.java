package pl.com.bottega.documentmanagement.application.mathfun;

import com.google.common.collect.Lists;
import pl.com.bottega.documentmanagement.application.templatemethod.CommandFactory;
import pl.com.bottega.documentmanagement.application.templatemethod.ConsoleApplication;

import java.util.Collection;

/**
 * Created by Dell on 2016-08-26.
 */
public class MathFunApp extends ConsoleApplication {

    @Override
    protected CommandFactory commandFactory() {
        return new CalculatorCommandFactory();
    }

    @Override
    protected Collection<String> menuItems() {
        return Lists.newArrayList(
                "1. Solve a quadratic equation",
                "2. Calculate the sine",
                "3. Calculate the cosine",
                "4. Calculate 2 to the power of x");
    }

    public static void main(String[] args) {
        new MathFunApp().run();
    }
}
