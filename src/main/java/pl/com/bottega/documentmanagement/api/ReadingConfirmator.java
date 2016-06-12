package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
public class ReadingConfirmator {

    public void confirm(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
    }

    public void confirm(DocumentNumber documentNumber, Employee forEmployee) {
        checkNotNull(documentNumber);
        checkNotNull(forEmployee);

    }

}
