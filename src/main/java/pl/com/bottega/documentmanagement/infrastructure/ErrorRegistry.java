package pl.com.bottega.documentmanagement.infrastructure;

import java.util.*;

/**
 * Created by anna on 20.08.2016.
 */
public class ErrorRegistry {

    private static ErrorRegistry singleton;

    private ErrorRegistry() {

    }

    private List<String> errors = new LinkedList<>();

    public synchronized static ErrorRegistry getInstance() {
        if(singleton == null)
            synchronized (ErrorRegistry.class) {
                if (singleton == null)
                    singleton = new ErrorRegistry();
            }
        return singleton;
    }

    public void registerError(String error) {
        errors.add(error);
    }
}
