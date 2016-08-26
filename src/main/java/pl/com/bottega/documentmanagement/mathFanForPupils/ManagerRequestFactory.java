package pl.com.bottega.documentmanagement.mathFanForPupils;

/**
 * Created by bartosz.paszkowski on 26.08.2016.
 */
public class ManagerRequestFactory implements RequestFactory {

    Request request;

    @Override
    public Request createRequest(String req) {
        if (req.equals("1"))
            request = new QuadraticEquation();
        else if (req.equals("2"))
            request = new CalculateSinus();
        else if (req.equals("3"))
            request = new CalculateCosine();
        else if (req.equals("4"))
            request = new CalculateTwoToPower();
        else {
            request = new WrongRequest();


        }
        return request;
    }
}
