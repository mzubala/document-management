package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
public class ReadingConfirmator {

    private DocumentRepository documentRepository;
    private UserManager userManager;
    private EmployeeRepository employeeRepository;

    public ReadingConfirmator(DocumentRepository documentRepository, UserManager userManager, EmployeeRepository employeeRepository) {
        this.documentRepository = documentRepository;
        this.userManager = userManager;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void confirm(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee());
        documentRepository.save(document);
    }

    @Transactional
    public void confirm(DocumentNumber documentNumber, EmployeeId targetEmployeeIn) {
        checkNotNull(documentNumber);
        if (targetEmployeeIn.isNull()) {
            confirm(documentNumber);
            return;
        }

        Document document = documentRepository.load(documentNumber);

        Employee targetEmployee = employeeRepository.findByEmployeeId(targetEmployeeIn);
        Employee confirmBy = userManager.currentEmployee();

        document.confirm(targetEmployee, confirmBy);
        documentRepository.save(document);
    }
}
