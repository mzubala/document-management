package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Dell on 2016-07-03.
 */
@RestController
@RequestMapping("/documents")
public class DocumentsController {

    private DocumentFlowProcess documentFlowProcess;

    public DocumentsController(DocumentFlowProcess documentFlowProcess) {
        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public DocumentNumber create(@RequestBody DocumentRequest documentRequest) {
        return documentFlowProcess.create(documentRequest.getTitle(), documentRequest.getContent());
    }

    @PostMapping ("/{documentNumber}")
    public void change(@PathVariable String documentNumber, @RequestBody DocumentRequest documentRequest) {
        DocumentNumber number = new DocumentNumber(documentNumber);
        documentFlowProcess.change(number, documentRequest.getTitle(), documentRequest.getContent());
    }
}
