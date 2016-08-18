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
    @RequiresAuth(roles = "STAFF")
    public void confirm(DocumentNumber documentNumber) throws IllegalArgumentException {
        checkNotNull(documentNumber);

        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee());
        documentRepository.save(document);
    }

    @Transactional
    @RequiresAuth(roles = "MANAGER")
    public void confirm(DocumentNumber documentNumber, EmployeeId forEmployee) throws IllegalArgumentException {
        checkNotNull(documentNumber);
        checkNotNull(forEmployee);

        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee(), employeeRepository.findByEmployeeId(forEmployee));

        documentRepository.save(document);
    }

}
