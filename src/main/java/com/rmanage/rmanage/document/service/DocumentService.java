package com.rmanage.rmanage.document.service;

import com.rmanage.rmanage.document.config.s3Setting.S3Uploader;
import com.rmanage.rmanage.document.dto.ResponseDto;
import com.rmanage.rmanage.document.dto.ResultDto;
import com.rmanage.rmanage.document.repository.DocumentRepository;
import com.rmanage.rmanage.entity.Document;
import com.rmanage.rmanage.entity.Worker;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;
    private EntityManager entityManager;
    private S3Uploader s3Uploader;

    @Autowired
    public DocumentService(DocumentRepository theDocumentRepository, EntityManager entityManager, S3Uploader s3Uploader){
        this.documentRepository = theDocumentRepository;
        this.entityManager = entityManager;
        this.s3Uploader = s3Uploader;
    }

    public ResponseDto getDocuments(int workerId) {
        try {
            Document document = null;
            // 근무 근로자 찾기
            Worker worker = entityManager.find(Worker.class, workerId);
            if(worker == null){
                return new ResponseDto(false,3013,"해당하는 근무지, 근로자 정보가 없음",null);
            }
            // 근무 근로자 아이디로 문서 찾기
            List<Document> documents = documentRepository.findDocumentByWorker(worker);
            // 조회 성공
            List<ResultDto> responseDocuments = new ArrayList<>();
            for(Document d : documents){
                responseDocuments.add(new ResultDto(d.getDocumentId(),d.getType(),d.getImageUrl(),d.getExpireDate()));
            }
            return new ResponseDto(true,1011,"서류 조회 성공",responseDocuments);
        }   catch (Exception e){
            System.out.println(e);
            return new ResponseDto(false,3011,"서류 조회 실패",null);
        }
    }

    public ResponseDto postDocument(int workerId, String type, LocalDate expireDate, MultipartFile image) {
                try {
                    // 근무 근로자 조회
                    Worker worker = entityManager.find(Worker.class, workerId);
                    if(worker == null){
                        return new ResponseDto(false,3013,"해당하는 근무지, 근로자 정보가 없음",null);
                    }
                    // 이미지 업로드
                    String filename = null;
                    filename = s3Uploader.uploadFiles(image, "image");
                    if(filename == null){
                        return new ResponseDto(false,2030,"이미지 업로드에 실패함",null);
                    }
                    // 서류 등록 성공
                    Document document = new Document(worker.getUser(), worker.getWorkPlace(), type, expireDate, worker, filename);
                    Document theDocument = documentRepository.save(document);
                    List<ResultDto> resultDto = List.of(new ResultDto(theDocument.getDocumentId(), theDocument.getType(), theDocument.getImageUrl(), theDocument.getExpireDate()));
                    return new ResponseDto(true,1012,"서류 등록 성공", resultDto);
                }   catch (Exception e) {
                    System.out.println(e);
                    return new ResponseDto(false,3012,"서류 등록 실패",null);
                }
    }

    public ResponseDto deleteDocument(Long documentId) {
        try {
            // 근무 근로자 조회
            Optional<Document> document = documentRepository.findById(documentId);
            // 이미지 삭제
            if(document.isEmpty()){
                return new ResponseDto(false,3013,"해당하는 문서가 존재하지 않음",null);
            }
            Document document1 = document.get();
            System.out.println(document1);
            // 이미지 삭제
            s3Uploader.fileDelete(document1.getImageUrl());
            // 서류 삭제 성공
            documentRepository.delete(document1);
            return new ResponseDto(true,1014,"서류 삭제 성공",null);
        }   catch (Exception e) {
            System.out.println(e);
            return new ResponseDto(false,3014,"서류 삭제 실패",null);
        }
    }

    public ResponseDto putDocument(Long documentId, String type, LocalDate expireDate, MultipartFile image) {
        try {
            // 근무 근로자 조회
            Optional<Document> document = documentRepository.findById(documentId);
            if(document.isEmpty()){
                return new ResponseDto(false,3013,"해당하는 문서가 존재하지 않음.",null);
            }
            Document document1 = document.get();
            // 이미지 수정
            String filename = null;
            filename = s3Uploader.uploadFiles(image, "image");
            if(filename == null){
                return new ResponseDto(false,2030,"이미지 업로드에 실패함.",null);
            } else{
                s3Uploader.fileDelete(document1.getImageUrl());
            }
            // 서류 수정 성공
            Document document2 = new Document(document1.getDocumentId(),document1.getUser(),
                    document1.getWorkPlace(),document1.getWorker(),type,filename,expireDate);
            Document document3 = documentRepository.save(document2);
            List<ResultDto> resultDto = List.of(new ResultDto(document3.getDocumentId(), document3.getType(), document3.getImageUrl(), document3.getExpireDate()));
            return new ResponseDto(true,1013,"서류 수정 성공", resultDto);
        }   catch (Exception e) {
            System.out.println(e);
            return new ResponseDto(false,3013,"서류 수정 실패.",null);
        }
    }


}

