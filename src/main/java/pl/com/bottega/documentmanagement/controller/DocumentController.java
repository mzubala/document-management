package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Admin on 03.07.2016.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {
    private DocumentFlowProcess documentFlowProcess;

    public DocumentController(DocumentFlowProcess documentFlowProcess) {
        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public DocumentNumber create(@RequestBody DocumentRequest documentRequest) {
        return documentFlowProcess.create(documentRequest.getTitle(), documentRequest.getContent());
    }

    @PostMapping(path = "/{documentNumber}")
    public void change(@PathVariable("documentNumber") DocumentNumber documentNumber, @RequestBody DocumentRequest documentRequest) {
        documentFlowProcess.change(documentNumber, documentRequest.getTitle(), documentRequest.getContent());
    }
}
