package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by maciuch on 07.07.16.
 */
@RestController
@RequestMapping("/documents/{documentNumber}/verifications")
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
