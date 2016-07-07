package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

/**
 * Created by Wojciech Winiarski on 03.07.2016.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private DocumentFlowProcess documentFlowProcess;


    public DocumentController(DocumentFlowProcess documentFlowProcess) {

        this.documentFlowProcess = documentFlowProcess;
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
}


