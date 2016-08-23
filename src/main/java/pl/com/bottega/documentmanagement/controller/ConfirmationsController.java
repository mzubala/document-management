package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.ReadingConfirmator;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by maciuch on 16.08.16.
 */
@RestController
@RequestMapping("/documents/{documentNumber}/confirmations")
public class ConfirmationsController {

    private ReadingConfirmator readingConfirmator;

    public ConfirmationsController(ReadingConfirmator readingConfirmator) {
        this.readingConfirmator = readingConfirmator;
    }

    @PutMapping
    public void create(@PathVariable String documentNumber) {
        readingConfirmator.confirm(new DocumentNumber(documentNumber));
    }

    @PutMapping("/{forEmployeeId}")
    public void create(@PathVariable String documentNumber, @PathVariable Long forEmployeeId) {
        readingConfirmator.confirm(new DocumentNumber(documentNumber), new EmployeeId(forEmployeeId));
    }

}
