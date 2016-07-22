package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by paulina.pislewicz on 2016-07-05.
 */
@RestController
@RequestMapping("/documents/{nr}/verifications")
public class VerificationsController {
    DocumentFlowProcess documentFlowProcess;

    public VerificationsController(DocumentFlowProcess documentFlowProcess){
         this.documentFlowProcess = documentFlowProcess;

    }


    @PutMapping
    public void create(@PathVariable String nr){
        documentFlowProcess.verify(new DocumentNumber(nr));
        }
}
