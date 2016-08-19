package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dell on 2016-08-17.
 */
@RestController
@RequestMapping("documents/{documentNumber}/publication")
public class PublicationController {

    private DocumentFlowProcess documentFlowProcess;

    public PublicationController(DocumentFlowProcess documentFlowProcess) {
        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public void publish(@PathVariable String documentNumber, @RequestBody PublicationsRequest publicationsRequest) {
        Set<EmployeeId> ids = new HashSet<>();
        for (Long id : publicationsRequest.getEmployeeIds())
            ids.add(new EmployeeId(id));
        documentFlowProcess.publish(new DocumentNumber(documentNumber), ids);
    }

}
