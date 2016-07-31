package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.NPlus1SelectSimulator;

/**
 * Created by anna on 30.07.2016.
 */
public class NPlus1SelectTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        NPlus1SelectSimulator simulator = applicationContext.getBean(NPlus1SelectSimulator.class);
        //simulator.insertTestData();
        simulator.simulate();
    }
}
