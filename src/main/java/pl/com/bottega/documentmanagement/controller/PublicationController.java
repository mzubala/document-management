package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Seta on 2016-08-18.
 */
@RestController
@RequestMapping("/documents/{documentNumber}/publication")
public class PublicationController {

    private DocumentFlowProcess documentFlowProcess;

    public PublicationController(DocumentFlowProcess documentFlowProcess){
        this.documentFlowProcess = documentFlowProcess;
    }
    @PutMapping
    public void publish(@PathVariable String documentNumber, @RequestBody PublicationRequest publicationRequest){
        Set<Long> ids = new HashSet<>();
        for (Long id: publicationRequest.getEmployeeIds()){
            ids.add(id);
        }

        documentFlowProcess.publish(new DocumentNumber(documentNumber), ids);
    }

}
