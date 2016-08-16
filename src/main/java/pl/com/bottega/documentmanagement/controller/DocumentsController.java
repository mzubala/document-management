package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by maciuch on 03.07.16.
 */
@RestController
@RequestMapping("/documents")
public class DocumentsController {

    private DocumentFlowProcess documentFlowProcess;
    private DocumentsCatalog documentsCatalog;
    private ReadingConfirmator readingConfirmator;

    public DocumentsController(DocumentFlowProcess documentFlowProcess, DocumentsCatalog documentsCatalog, ReadingConfirmator readingConfirmator) {
        this.documentFlowProcess = documentFlowProcess;
        this.documentsCatalog = documentsCatalog;
        this.readingConfirmator = readingConfirmator;
    }

    @PutMapping
    public DocumentNumber create(@RequestBody DocumentRequest documentRequest) {
        return documentFlowProcess.create(documentRequest.getTitle(), documentRequest.getContent());
    }

    @PostMapping("/{documentNumber}")
    public void update(@PathVariable DocumentNumber documentNumber, @RequestBody DocumentRequest documentRequest) {
        documentFlowProcess.change(documentNumber, documentRequest.getTitle(), documentRequest.getContent());
    }

    @GetMapping("/{documentNumber}")
    public DocumentDto show(@PathVariable DocumentNumber documentNumber) {
        return documentsCatalog.get(documentNumber);
    }

    @GetMapping
    public DocumentSearchResults index(DocumentCriteria documentCriteria) {
        return documentsCatalog.find(documentCriteria);
    }

    @DeleteMapping("/{documentNumber}")
    public void delete(@PathVariable DocumentNumber documentNumber) {
        documentFlowProcess.delete(documentNumber);
    }

    @PutMapping("/publish/{documentNumber}")
    public void publish(@PathVariable DocumentNumber documentNumber, @RequestBody PublishRequest pubReq) {
        documentFlowProcess.publish(documentNumber, pubReq.getTargetEmployees());
    }

    @PutMapping("/confirm/{documentNumber}")
    public void confirm(@PathVariable DocumentNumber documentNumber, @RequestBody EmployeeId employeeId) {
        System.out.println("Подтвердить документ " + documentNumber + " Для работника " + employeeId);
        readingConfirmator.confirm(documentNumber, employeeId);
    }
}
