package com.sanr.dms.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sanr.dms.dto.DocumentDto;
import com.sanr.dms.entity.Document;
import com.sanr.dms.repository.DocumentRepository;


@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentDto getDocument(BigInteger id){
        Optional<Document> optioonalDocument = documentRepository.findById(id);
        return optioonalDocument.map(DocumentDto::convert).orElse(null);
    }

    public Document updateDocument(BigInteger id, DocumentDto documentDto){
        Optional<Document> optionalDocument = documentRepository.findById(id);
        Document document = optionalDocument.orElse(null);
        if( document!= null){
            if(documentDto.getName()!=null){
                document.setName(documentDto.getName());
            }
            if(documentDto.getBody()!=null){
                document.setBody(documentDto.getBody());
            }
            if(documentDto.getDocumentType()!=null){
                document.setDocumentType(documentDto.getDocumentType());
            }
            if(documentDto.getSignDate()!=null){
                document.setSignDate(documentDto.getSignDate());
            }
            if(documentDto.getUser()!=null){
                document.setUser(documentDto.getUser());
            }
            
            System.out.println(documentDto.getName());
        }
        documentRepository.saveAndFlush(document);
        return document;
    }

    public Boolean deleteDocument(BigInteger id){
        Optional<Document> documentOptional = documentRepository.findById(id);
        Document document = documentOptional.orElse(null);
        Boolean result = false;
        if(document != null){
            documentRepository.delete(document);
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    public List<DocumentDto> getUserDocuments(String name){
        List<Document> documents = documentRepository.findByUser(name);
        return documents.stream().map(DocumentDto::convert).collect(Collectors.toList());
    }

    public List<DocumentDto> getDocumentsBySignDateRange(Date dateFrom, Date dateTo){
        List<Document> documents = documentRepository.findBySignDateBetween(dateFrom, dateTo);
        return documents.stream().map(DocumentDto::convert).collect(Collectors.toList());
    }

    public List<DocumentDto> getDocumentsByCreateDateRange(Date dateFrom, Date dateTo){
        List<Document> documents = documentRepository.findByCreatedDateBetween(dateFrom, dateTo);
        return documents.stream().map(DocumentDto::convert).collect(Collectors.toList());
    }

    public List<DocumentDto> getSignedDocuments(){
        List<Document> documents = documentRepository.findBySignDateIsNotNull();
        return documents.stream().map(DocumentDto::convert).collect(Collectors.toList());
    }

    public List<DocumentDto> getUnSignedDocuments(){
        List<Document> documents = documentRepository.findBySignDateIsNull();
        return documents.stream().map(DocumentDto::convert).collect(Collectors.toList());
    }

    public DocumentDto create(DocumentDto documentDto){
        Document document = DocumentDto.convert(documentDto);
        document =  documentRepository.saveAndFlush(document);
        return (DocumentDto) DocumentDto.convert(document);
    }

}
