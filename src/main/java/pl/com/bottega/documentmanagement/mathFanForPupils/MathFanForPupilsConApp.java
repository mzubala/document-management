package pl.com.bottega.documentmanagement.mathFanForPupils;

import java.util.Collection;
import java.util.Scanner;

/**
 * Created by bartosz.paszkowski on 26.08.2016.
 */
public abstract class MathFanForPupilsConApp {

    protected Scanner scanner = new Scanner(System.in);


    public void run(){
        while(true){
            printMenu();
            String request = getRequest();
            if (request.equals("quit"))
                return;
            execute(request);
        }

    }

    private void printMenu() {
        Collection<String> menu = menu();
        for (String item : menu){
            System.out.println(item);
        }
    }

    private String getRequest() {
        return scanner.nextLine();
    }


    private void execute(String req) {
        RequestFactory requestFactory = requestFactory();
        Request request = requestFactory.createRequest(req);
        request.execute();

    }

    protected abstract Collection<String> menu();
    protected abstract RequestFactory requestFactory();


}
