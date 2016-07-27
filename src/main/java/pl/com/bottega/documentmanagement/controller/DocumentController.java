package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Admin on 03.07.2016.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {
    private DocumentFlowProcess documentFlowProcess;
    private DocumentsCatalog documentsCatalog;

    public DocumentController(DocumentFlowProcess documentFlowProcess, DocumentsCatalog documentsCatalog) {
        this.documentFlowProcess = documentFlowProcess;
        this.documentsCatalog = documentsCatalog;
    }

    @PutMapping
    public DocumentNumber create(@RequestBody DocumentRequest documentRequest) {
        return documentFlowProcess.create(documentRequest.getTitle(), documentRequest.getContent());
    }

    @PostMapping(path = "/{documentNumber}")
    public void change(@PathVariable DocumentNumber documentNumber, @RequestBody DocumentRequest documentRequest) {
        documentFlowProcess.change(documentNumber, documentRequest.getTitle(), documentRequest.getContent());
    }

    @GetMapping("/{documentNumber}")
    public DocumentDto show(@PathVariable DocumentNumber documentNumber) {
        return documentsCatalog.get(documentNumber);
    }

    @GetMapping
    public Iterable<DocumentDto> index(DocumentCriteria documentCriteria) {
        return documentsCatalog.find(documentCriteria);
    }

    @DeleteMapping("/{documentNumber}")
    public void delete(@PathVariable DocumentNumber documentNumber) {
        documentFlowProcess.delete(documentNumber);
    }
}
