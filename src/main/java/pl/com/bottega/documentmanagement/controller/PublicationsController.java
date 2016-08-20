package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by maciuch on 16.08.16.
 */
@RestController
@RequestMapping("/documents/{documentNumber}/publications")
public class PublicationsController {

    private DocumentFlowProcess documentFlowProcess;


    public PublicationsController(DocumentFlowProcess documentFlowProcess) {
        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public void create(@PathVariable String documentNumber, @RequestBody PublicationRequest publicationRequest) {
        Set<EmployeeId> employeeIds = publicationRequest.getEmployeeIds().
                stream().map(EmployeeId::new).collect(Collectors.toSet());
        documentFlowProcess.publish(new DocumentNumber(documentNumber), employeeIds);
    }

}
