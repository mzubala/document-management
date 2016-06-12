package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by bartosz.paszkowski on 12.06.2016.
 */
public class ReadingCofirmator {

    public void confirm(DocumentNumber documentNumber){
        checkNotNull(documentNumber);

    }

    public void confirm(DocumentNumber documentNumber, Employee forEmplyee){
        checkNotNull(documentNumber);
        checkNotNull(forEmplyee);

    }

}
