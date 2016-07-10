package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

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

}

