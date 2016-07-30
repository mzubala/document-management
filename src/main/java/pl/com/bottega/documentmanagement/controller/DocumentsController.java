package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by maciuch on 03.07.16.
 */
@RestController
@RequestMapping("/documents")
public class DocumentsController {

    private DocumentFlowProcess documentFlowProcess;
    private DocumentsCatalog documentsCatalog;

    public DocumentsController(DocumentFlowProcess documentFlowProcess, DocumentsCatalog documentsCatalog) {
        this.documentFlowProcess = documentFlowProcess;
        this.documentsCatalog = documentsCatalog;
    }

    @PutMapping
    public DocumentNumber create(@RequestBody DocumentRequest documentRequest) {
        return documentFlowProcess.create(documentRequest.getTitle(), documentRequest.getContent());
    }

    @PostMapping("/{documentNumber}")
    public void update(@PathVariable String documentNumber, @RequestBody DocumentRequest documentRequest) {
        documentFlowProcess.change(new DocumentNumber(documentNumber), documentRequest.getTitle(), documentRequest.getContent());
    }

    @GetMapping("/{documentNumber}")
    public DocumentDto show(@PathVariable String documentNumber) {
        return documentsCatalog.get(new DocumentNumber(documentNumber));
    }

    // /documents?query=hospital&createdBy=40&verifiedBy=76&...&...
    @GetMapping
    public DocumentSearchResults index(DocumentCriteria documentCriteria) {
        return documentsCatalog.find(documentCriteria);
    }

    @DeleteMapping("/{documentNumber}")
    public void destroy(@PathVariable String documentNumber) {
        documentFlowProcess.archive(new DocumentNumber(documentNumber));
    }

}
