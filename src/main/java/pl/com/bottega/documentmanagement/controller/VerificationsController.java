package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Dell on 2016-07-06.
 */
@RestController
@RequestMapping("/documents/{documentNumber}/verification")
public class VerificationsController {

    private DocumentFlowProcess documentFlowProcess;

    public VerificationsController(DocumentFlowProcess documentFlowProcess) {
        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public void create(@PathVariable String documentNumber) {
        documentFlowProcess.verify(new DocumentNumber(documentNumber));
    }
}
