package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;

/**
 * Created by paulina.pislewicz on 2016-07-05.
 */
@RestController
@RequestMapping("/documents/{nr}")
public class VerificationsController {
    DocumentFlowProcess documentFlowProcess;

    public VerificationsController(DocumentFlowProcess documentFlowProcess){
         this.documentFlowProcess = documentFlowProcess;

    }

    @PutMapping(path = "/verification", headers = "myHeader=myValue")
    public void verify(@PathVariable String nr,  VerificationRequest verificationRequest){
        documentFlowProcess.verify(documentFlowProcess.create(verificationRequest.getTitle(), verificationRequest.getContent()));
        }
}
