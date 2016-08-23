package pl.com.bottega.documentmanagement.infrastructure;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bartosz.paszkowski on 20.08.2016.
 */
public class ErrorRegistry2 {

    private static class SingletonHolder{
        private static final ErrorRegistry2 INSTANCE = new ErrorRegistry2();
    }
    private List<String> errors = new LinkedList<>();

    private ErrorRegistry2(){
        System.out.println("Creating error registry 2");
    }


    public synchronized static ErrorRegistry2 getInstance (){
        return SingletonHolder.INSTANCE;
    }

    public void registerError(String error){
        errors.add(error);
    }
}
