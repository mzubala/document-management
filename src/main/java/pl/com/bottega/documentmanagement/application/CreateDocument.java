package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by maciuch on 18.06.16.
 */
public class CreateDocument {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"application.xml"});

    }

}
