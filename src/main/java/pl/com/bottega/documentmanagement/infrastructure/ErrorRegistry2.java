package pl.com.bottega.documentmanagement.infrastructure;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dell on 2016-08-20.
 */
public class ErrorRegistry2 {

    private static class SingletonHolder {
        private static final ErrorRegistry2 INSTANCE = new ErrorRegistry2(); //wykona się dopiero w momencie pierwszego użycia
    }

    private List<String> errors = new LinkedList<>();

    private ErrorRegistry2() {
        System.out.println("ErrorRegistry2");
    }

    public static ErrorRegistry2 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void registerError(String error) {
        errors.add(error);
    }
}
