package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.NPlus1SelectSimulator;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.Tag;

/**
 * Created by maciuch on 30.07.16.
 */
public class NPlus1SelectTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        NPlus1SelectSimulator simulator = applicationContext.getBean(NPlus1SelectSimulator.class);
        //simulator.insertTestData();
        //simulator.simulate();
        Document d = simulator.getDocument();
        System.out.println(d.getVerificator().toString());
        for(Tag t : d.tags())
            System.out.println(t);
    }

}
