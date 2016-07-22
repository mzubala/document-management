package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.api.DocumentDto;

/**
 * Created by paulina.pislewicz on 2016-07-03.
 */
@RestController
@RequestMapping ("/documents")
public class DocumentController {
    private DocumentFlowProcess documentFlowProcess;
    private DocumentsCatalog documentsCatalog;

    public DocumentController (DocumentFlowProcess documentFlowProcess, DocumentsCatalog documentsCatalog){
        this.documentFlowProcess = documentFlowProcess;
        this.documentsCatalog = documentsCatalog;
    }

    @PutMapping
    //@RequestMapping (path="/documents", method = RequestMethod.PUT)- jak usuwamy @RequestMapping nad klasą
    public DocumentNumber create(@RequestBody DocumentRequest documentRequest){
        return documentFlowProcess.create(documentRequest.getTitle(), documentRequest.getContent());
    }

    @PostMapping("/{documentNumber}")
   // @RequestMapping (path="/documents/{nr}", method = RequestMethod.POST) tak może być jak usuwamy @RequestMapping nad klasą
    public void update (@PathVariable String nr, @RequestBody DocumentRequest documentRequest){
        documentFlowProcess.change(new DocumentNumber(nr) ,documentRequest.getTitle(), documentRequest.getContent());
    }

    @GetMapping("/{documentNumber}")
    public DocumentDto show(@PathVariable String documentNumber){
        return documentsCatalog.get(new DocumentNumber(documentNumber));
    }

    ///documents?query=hospital&createdFrom=3000&createdUntil=6000...Spring mvc potrafi stworzyć z tego URL documentCriteria
    @GetMapping
    public Iterable <DocumentDto> index(DocumentCriteria documentCriteria){ //metoda zwraca kilka dokumentów a nie 1
        return documentsCatalog.find(documentCriteria);
    }

    //@DeleteMapping ("/{documentNumber}")
    @RequestMapping(value = "/{documentNumber}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String documentNumber){
        documentFlowProcess.delete(new DocumentNumber(documentNumber));
    }
}
