package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.ReadingConfirmator;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by paulina.pislewicz on 2016-08-17.
 */
@RestController
@RequestMapping
public class ConfirmationsController {
    public ReadingConfirmator readingConfirmator;

    public ConfirmationsController (ReadingConfirmator readingConfirmator){
        this.readingConfirmator = readingConfirmator;
    }

    @PutMapping ("/documents/{nr}/confirmations")
    public void create(@PathVariable String nr){
        readingConfirmator.confirm(new DocumentNumber(nr));
    }

    @PutMapping ("/documents/{nr}/confirmations")
    public void create(@PathVariable String nr, Long forId){
        readingConfirmator.confirm(new DocumentNumber(nr), new EmployeeId(forId));
    }


}
