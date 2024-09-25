package com.sanr.dms.controller;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import com.sanr.dms.dto.DocumentDto;
import com.sanr.dms.service.DocumentService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(path = "/document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("")
    public DocumentDto getDocument(@RequestParam BigInteger id) {
        return documentService.getDocument(id); 
    }

    @PostMapping("")
    public ResponseEntity<DocumentDto> createDocument(@RequestBody DocumentDto documentDto) {
        DocumentDto newDocument = documentService.create(documentDto);
        System.out.println(newDocument.getId());
        return ResponseEntity.ok().body(newDocument);
    }

    @PutMapping("")
    public String updateDocument(@RequestParam BigInteger id, @RequestBody DocumentDto documentDto) {
        documentService.updateDocument(id, documentDto);
        return "Ok Updated";
    }

    @DeleteMapping()
    public String deleteDocument(@RequestParam BigInteger id){
        Boolean result = documentService.deleteDocument(id);
        String resultStr = result?"OK. DELETED":"ERROR. OBJECT MISSING";
        return resultStr;
    }

    @GetMapping("/user")
    public List<DocumentDto> getUserDocument(@RequestParam String name) {
        return documentService.getUserDocuments(name);
    }

    @GetMapping("/get-by-created-date")
    public List<DocumentDto> getMethodName(@RequestParam("dateFrom") Date dateFrom, @RequestParam("dateTo") Date dateTo) {
        return documentService.getDocumentsByCreateDateRange(dateFrom, dateTo);
    }

    @GetMapping("/signed")
    public List<DocumentDto> getMethodName(@RequestParam("status") Boolean status) {
        return status?documentService.getSignedDocuments():documentService.getUnSignedDocuments();
    }
}
