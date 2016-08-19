package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;

/**
 * Created by paulina.pislewicz on 2016-08-17.
 */
@RestController
@RequestMapping("/documents/{nr}/publications")
public class PublicationsController {
    DocumentFlowProcess documentFlowProcess;

    public PublicationsController (DocumentFlowProcess documentFlowProcess){
        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public void create (@PathVariable String nr, Set<EmployeeId> employeeIds){
        documentFlowProcess.publish(new DocumentNumber(nr), employeeIds );
    }

}
