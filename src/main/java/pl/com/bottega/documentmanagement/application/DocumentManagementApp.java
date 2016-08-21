package pl.com.bottega.documentmanagement.application;

import com.google.common.collect.Lists;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;

import java.util.Collection;
import java.util.Scanner;

/**
 * Created by maciuch on 21.08.16.
 */
public class DocumentManagementApp extends ConsoleApplication {

    private ApplicationContext applicationContext;

    public DocumentManagementApp() {
        applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        promptLogin();
    }

    private void promptLogin() {
        System.out.print("Login: ");
        String login = new Scanner(System.in).nextLine();
        System.out.print("Password: ");
        String password = new Scanner(System.in).nextLine();
        UserManager userManager = applicationContext.getBean(UserManager.class);
        SignupResultDto signupResultDto = userManager.login(login, password);
        if(!signupResultDto.isSuccess()) {
            System.out.println(signupResultDto.getFailureReason());
            promptLogin();
        }
    }

    @Override
    protected CommandFactory commandFactory() {
        return new DocumentManagementCommandFactory(applicationContext);
    }

    @Override
    protected Collection<String> menuItems() {
        return Lists.newArrayList(
                "1. Create document",
                "2. Search documents",
                "3. Edit document",
                "4. Verify document"
        );

    }

    public static void main(String[] args) {
        new DocumentManagementApp().run();
    }

}
