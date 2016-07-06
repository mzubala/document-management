package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by paulina.pislewicz on 2016-07-03.
 */
@RestController
@RequestMapping ("/documents")
public class DocumentController {
    private DocumentFlowProcess documentFlowProcess;

    public DocumentController (DocumentFlowProcess documentFlowProcess){
        this.documentFlowProcess = documentFlowProcess;
    }

    @PutMapping
    public DocumentNumber create(@RequestBody DocumentRequest documentRequest){
        return documentFlowProcess.create(documentRequest.getTitle(), documentRequest.getContent());
    }

    @RequestMapping (path="/{nr}", method = RequestMethod.POST)
    public void change (@PathVariable String nr, @RequestBody DocumentRequest documentRequest){
        documentFlowProcess.change(new DocumentNumber(nr) ,documentRequest.getTitle(), documentRequest.getContent());
    }

}
