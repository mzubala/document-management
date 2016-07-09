package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

/**
 * Created by Seta on 2016-07-07.
 */
@RestController
@RequestMapping("/documents/{documentNumber}/verification")
public class VerificationController {

    private DocumentFlowProcess documentFlowProcess;


    public VerificationController(DocumentFlowProcess documentFlowProcess){

        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public void create(@PathVariable String documentNumber){

        documentFlowProcess.verify(new DocumentNumber(documentNumber));

    }



}
