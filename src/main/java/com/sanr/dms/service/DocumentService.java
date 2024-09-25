package com.sanr.dms.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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

    public List<Document> getUserDocuments(String name){
        return documentRepository.findByUser(name);
    }

    public List<Document> getDocumentsBySignDateRange(Date dateFrom, Date dateTo){
        return documentRepository.findBySignDateBetween(dateFrom, dateTo);
    }

    public List<Document> getDocumentsByCreateDateRange(Date dateFrom, Date dateTo){
        return documentRepository.findByCreatedDateBetween(dateFrom, dateTo);
    }

    public List<Document> getSignedDocuments(){
        return documentRepository.findBySignDateIsNotNull();
    }

    public List<Document> getUnSignedDocuments(){
        return documentRepository.findBySignDateIsNull();
    }

    public Document create(Document document){
        return documentRepository.saveAndFlush(document);
    }

}
