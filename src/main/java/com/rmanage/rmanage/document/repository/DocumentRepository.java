package com.rmanage.rmanage.document.repository;

import com.rmanage.rmanage.entity.Document;
import com.rmanage.rmanage.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findDocumentByWorker(Worker worker);

    Document findDocumentByType(String type);

}
