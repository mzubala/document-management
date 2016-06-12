package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by paulina.pislewicz on 2016-06-12.
 */
public class ReadingConfirmator {
    public void confirm (DocumentNumber documentNumber){
        checkNotNull(documentNumber);

    }
    public void confirm (DocumentNumber documentNumber, Employee forEmployee){
        checkNotNull(documentNumber);
        checkNotNull(forEmployee);

    }
}
