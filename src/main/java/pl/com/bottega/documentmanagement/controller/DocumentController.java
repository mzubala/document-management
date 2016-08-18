package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import javax.persistence.Id;

/**
 * Created by Wojciech Winiarski on 03.07.2016.
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
    public DocumentNumber create(@RequestBody DocumentRequest createRequest) {

        return documentFlowProcess.create(createRequest.getTitle(), createRequest.getContent());

    }

    @PostMapping("/{documentNumber}")
    public void update(@PathVariable String documentNumber,
                       @RequestBody DocumentRequest documentRequest) {
        documentFlowProcess.change(new DocumentNumber(documentNumber), documentRequest.getTitle(),
                documentRequest.getContent());

    }
    @GetMapping("/{documentNumber}")
    public DocumentDto show(@PathVariable String documentNumber){

        return documentsCatalog.get(new DocumentNumber(documentNumber));
    }

    // /documents?query=hospital&createdBy=40&verifiedBy=76...&..
    @GetMapping
    public DocumentSearchResults index(DocumentCriteria documentCriteria){
    return documentsCatalog.find(documentCriteria);

    }
    @DeleteMapping("/{documentNumber}")
    public void delete(@PathVariable String documentNumber){
        documentFlowProcess.archive(new DocumentNumber(documentNumber));

    }


}


