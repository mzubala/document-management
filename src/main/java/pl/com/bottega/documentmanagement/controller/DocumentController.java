package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by bartosz.paszkowski on 03.07.2016.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private DocumentFlowProcess documentFlowProcess;
    private DocumentsCatalog documentsCatalog;

    public DocumentController(DocumentFlowProcess documentFlowProcess, DocumentsCatalog documentsCatalog){
        this.documentFlowProcess = documentFlowProcess;
        this.documentsCatalog = documentsCatalog;
    }

    // inny zapis @RequestMapping(path = "/documents", method = RequestMethod.PUT)
    // @PutMapping ("/documents")
    @PutMapping
    public DocumentNumber create(@RequestBody DocumentRequest documentRequest){
        return documentFlowProcess.create(documentRequest.getTitle(),documentRequest.getContent());
    }

    // POST/documents/ISO-XXX-YYYY-ZZZ
    // inny zapis @RequestMapping(path = "/documents/{documentNumber}", method = RequestMethod.POST)
    //@PostMapping("/documents/{documentNumber}")
    @PostMapping("/{documentNumber}")
    public void update(@PathVariable String documentNumber, @RequestBody DocumentRequest documentRequest){
        documentFlowProcess.change(new DocumentNumber(documentNumber),
                documentRequest.getTitle(),documentRequest.getContent());
    }

    @GetMapping("/{documentNumber}")
    public DocumentDto show(@PathVariable String documentNumber){
        return  documentsCatalog.get(new DocumentNumber(documentNumber));
    }

    // /documents?query=hospital&createdBy=40&verifiedBy=76&...&...&
    @GetMapping
    public DocumentSearchResults index (DocumentCriteria documentCriteria){
        return documentsCatalog.find(documentCriteria);
    }

    @DeleteMapping("/{documentNumber}")
    public void delete(@PathVariable String documentNumber){
        documentFlowProcess.archive(new DocumentNumber(documentNumber));
    }

}
