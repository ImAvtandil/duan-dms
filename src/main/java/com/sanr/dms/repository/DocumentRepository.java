package com.sanr.dms.repository;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sanr.dms.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, BigInteger>{

    List<Document> findByUser(String user);

    List <Document> findBySignDate(Date fromDate);

    List <Document> findBySignDateBetween(Date dateFrom, Date dateTo);

    List <Document> findByCreatedDateBetween(Date dateFrom, Date dateTo);

    List <Document> findBySignDateIsNotNull();

    List <Document> findBySignDateIsNull();
}
