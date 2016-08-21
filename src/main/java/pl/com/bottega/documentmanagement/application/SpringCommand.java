package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;

/**
 * Created by maciuch on 21.08.16.
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
