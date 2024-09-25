package com.sanr.dms.dto;

import java.math.BigInteger;
import java.sql.Date;

import com.sanr.dms.entity.Document;
import com.sanr.dms.entity.DocumentTypeEnum;

public class DocumentDto extends ConvertAbstract {

    private BigInteger id;
    private DocumentTypeEnum documentType;
    private String name;
    private String body;
    private Date createdDate;
    private Date signDate;
    private String user;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public DocumentTypeEnum getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeEnum documentType) {
        this.documentType = documentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static DocumentDto convert(Document document){
        DocumentDto documentDto = new DocumentDto();
        return (DocumentDto) convertFromTo(document, documentDto);
    }
    public static Document convert(DocumentDto documentDto){
        Document document = new Document();
        return (Document) convertFromTo(documentDto, document);
    }
}