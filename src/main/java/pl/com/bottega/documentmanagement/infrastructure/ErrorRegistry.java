package pl.com.bottega.documentmanagement.infrastructure;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dell on 2016-08-20.
 */
public class ErrorRegistry {

    private static ErrorRegistry singleton;

    private List<String> errors = new LinkedList<>();

    private ErrorRegistry() {}

    public static ErrorRegistry getInstance() {
        if(singleton == null)
            singleton = new ErrorRegistry();
        synchronized (ErrorRegistry.class) {
            if (singleton == null)
                return new ErrorRegistry();
        }
        return singleton;
    }

    public void registerError(String error) {
        errors.add(error);
    }
}
