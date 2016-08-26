package pl.com.bottega.mathplay;

import com.google.common.collect.Lists;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;

/**
 * Created by anna on 25.08.2016.
 */
public class MathPlayApplication extends ConsoleApplication {

    private ApplicationContext applicationContext;

    public MathPlayApplication() {
        applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
    }

    @Override
    protected CommandFactory commandFactory() {
        return new PlayMathCommandFactory(applicationContext);
    }

    @Override
    protected Collection<String> menuItems() {
        return Lists.newArrayList(
                "1. Solve quadratic equation",
                "2. Calculate sine",
                "3. Calculate cosine",
                "4. Calculate 2 to the power of x"
        );
    }

    public static void main(String[] args) {
        new MathPlayApplication().run();
    }
}
