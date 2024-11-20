package com.sanr.dms.controller;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import com.sanr.dms.dto.DocumentDto;
import com.sanr.dms.service.DocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Get a document by its id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Found the document", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = DocumentDto.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Document not found", 
            content = @Content) })
    @GetMapping("")
    public DocumentDto getDocument(@RequestParam BigInteger id) {
        return documentService.getDocument(id); 
    }

    @Operation(summary = "Create new document")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Document created successfully",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = DocumentDto.class)) }),
       @ApiResponse(responseCode = "400", description = "Invalid input provided") })
    @PostMapping("")
    public ResponseEntity<DocumentDto> createDocument(@io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Document to create", required = true,
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = DocumentDto.class),
        examples = @ExampleObject(value = "{ \"documentType\": \"VacationLeaveApplication\", \"name\": \"Some name\", \"body\": \"Some body\", \"user\": \"Jone\"}")))
      @RequestBody DocumentDto document) {
        System.out.println(document.getName());
        DocumentDto newDocument = documentService.create(document);
        System.out.println(newDocument.getId());
        return ResponseEntity.ok().body(newDocument);
    }

    @Operation(summary = "Update document")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document updated successfully",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = DocumentDto.class)) }),
        @ApiResponse(responseCode = "404", description = "Document with id not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PutMapping("")
    public String updateDocument(@RequestParam BigInteger id, @RequestBody DocumentDto documentDto) {
        documentService.updateDocument(id, documentDto);
        return "Ok Updated";
    }

    @Operation(summary = "Delete a document by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document deleted successfully")
    })
    @DeleteMapping()
    public String deleteDocument(@RequestParam BigInteger id){
        Boolean result = documentService.deleteDocument(id);
        String resultStr = result?"OK. DELETED":"ERROR. OBJECT MISSING";
        return resultStr;
    }
    
    @Operation(summary = "Get documents by owner (username)")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Found documents",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = DocumentDto.class)) }),
        @ApiResponse(responseCode = "400", description = "Invalid user supplied", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Documents not found", 
            content = @Content) })
    @GetMapping("/user")
    public List<DocumentDto> getUserDocument(@RequestParam String name) {
        return documentService.getUserDocuments(name);
    }

    @Operation(summary = "Get documents by create date range")
    @GetMapping("/get-by-created-date")
    public List<DocumentDto> getMethodName(@RequestParam("dateFrom") Date dateFrom, @RequestParam("dateTo") Date dateTo) {
        return documentService.getDocumentsByCreateDateRange(dateFrom, dateTo);
    }

    @Operation(summary = "Get signed documents")
    @GetMapping("/signed")
    public List<DocumentDto> getMethodName(@RequestParam("status") Boolean status) {
        return status?documentService.getSignedDocuments():documentService.getUnSignedDocuments();
    }
    
}
