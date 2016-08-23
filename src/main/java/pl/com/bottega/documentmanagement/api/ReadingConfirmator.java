package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
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
    private EmployeeRepository employeeRepository;
    private UserManager userManager;

    public ReadingConfirmator(DocumentRepository documentRepository, EmployeeRepository employeeRepository, UserManager userManager) {
        this.documentRepository = documentRepository;
        this.employeeRepository = employeeRepository;
        this.userManager = userManager;
    }
    @Transactional
    @RequiresAuth(roles = "STAFF")
    public void confirm(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee());
        documentRepository.save(document);
    }

    @Transactional
    @RequiresAuth(roles = "MANAGER")
    public void confirm(DocumentNumber documentNumber, EmployeeId forEmployeeId) {
        checkNotNull(documentNumber);
        checkNotNull(forEmployeeId);

        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee(), employeeRepository.findByEmployeeId(forEmployeeId));

        documentRepository.save(document);
    }

}
