package pl.com.bottega.documentmanagement.infrastructure;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciuch on 20.08.16.
 */
public class ErrorRegistry {

    private static ErrorRegistry singleton;

    private ErrorRegistry() {

    }

    private List<String> errors = new LinkedList<>();

    public static ErrorRegistry getInstance() {
        if (singleton == null)
            synchronized (ErrorRegistry.class) {
                if(singleton == null)
                    singleton = new ErrorRegistry();
            }
        return singleton;
    }

    public void registerError(String error) {
        errors.add(error);
    }

}
