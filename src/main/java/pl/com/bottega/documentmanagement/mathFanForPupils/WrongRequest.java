package pl.com.bottega.documentmanagement.mathFanForPupils;

/**
 * Created by bartosz.paszkowski on 26.08.2016.
 */
public class WrongRequest implements Request {
    @Override
    public void execute() {
        System.out.println("Zły wybór :(");
        System.out.println();
    }
}
