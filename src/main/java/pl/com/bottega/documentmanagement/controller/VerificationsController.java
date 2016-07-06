package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(path = "/verification", method = RequestMethod.PUT)
    public void verify(VerificationRequest verificationRequest){
        documentFlowProcess.verify(verificationRequest.getDocumentNumber());
    }
}
