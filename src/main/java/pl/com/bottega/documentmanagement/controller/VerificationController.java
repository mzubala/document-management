package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Admin on 08.07.2016.
 */
@RestController
@RequestMapping("/documents/{documentNumber}/verification")
public class VerificationController {
    private DocumentFlowProcess documentFlowProcess;

    public VerificationController(DocumentFlowProcess documentFlowProcess) {
        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public void verify(@PathVariable DocumentNumber documentNumber) {
        documentFlowProcess.verify(documentNumber);
    }
}
