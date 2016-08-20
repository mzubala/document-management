package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;
import java.util.stream.Collectors;

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
    public void publish (@PathVariable String nr, @RequestBody PublicationRequest publicationsRequest){
        Set<EmployeeId> employeeIds = publicationsRequest.getEmployeeIdsObligatedToRead().
                stream().map(EmployeeId::new).collect(Collectors.toSet());
        documentFlowProcess.publish(new DocumentNumber(nr), employeeIds);
    }

}
