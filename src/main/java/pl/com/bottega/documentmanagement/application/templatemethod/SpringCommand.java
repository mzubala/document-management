package pl.com.bottega.documentmanagement.application.templatemethod;

import org.springframework.context.ApplicationContext;
import pl.com.bottega.documentmanagement.application.templatemethod.Command;

/**
 * Created by Dell on 2016-08-21.
 */
public abstract class SpringCommand implements Command {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }
}
