package pl.com.bottega.documentmanagement.domain;

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
public class DocumentNumber {


    private String number;

    public DocumentNumber(String number) {
        this.number=number;
    }
    public String toString(){
        return number;
    }
}
