package pl.com.bottega.documentmanagement.application;

import pl.com.bottega.documentmanagement.infrastructure.ErrorRegistry2;

/**
 * Created by bartosz.paszkowski on 20.08.2016.
 */
public class SingletonTest {
    public static void main(String[] args) {
        System.out.println("Singleton Test");
        ErrorRegistry2 eerror = ErrorRegistry2.getInstance();
        eerror.registerError("str");
    }
}
