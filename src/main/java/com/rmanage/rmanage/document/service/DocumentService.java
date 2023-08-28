package com.rmanage.rmanage.document.service;

import com.rmanage.rmanage.document.dto.RequestDto;
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

    @Autowired
    public DocumentService(DocumentRepository theDocumentRepository, EntityManager entityManager){
        this.documentRepository = theDocumentRepository;
        this.entityManager = entityManager;
    }

    public ResponseDto getDocuments(int workerId) {
        try {
            Document document = null;
            // 근무 근로자 찾기
            Worker worker = entityManager.find(Worker.class, workerId);
            if(worker == null){
                return new ResponseDto(false,3041,"해당하는 근무지, 근로자 정보가 없음",null);
            }
            // 근무 근로자 아이디로 문서 찾기
            List<Document> documents = documentRepository.findDocumentByWorker(worker);
            // 조회 성공
            List<ResultDto> responseDocuments = new ArrayList<>();
            for(Document d : documents){
                responseDocuments.add(new ResultDto(d.getDocumentId(),d.getType(),d.getImageUrl(),d.getExpireDate()));
            }
            return new ResponseDto(true,1021,"서류 조회 성공",responseDocuments);
        }   catch (Exception e){
            System.out.println(e);
            return new ResponseDto(false,3005,"서류 조회 실패: " + e.toString(),null);
        }
    }

    public ResponseDto postDocument(int workerId, RequestDto requestDto) {
        try {
            // 근무 근로자 조회
            Worker worker = entityManager.find(Worker.class, workerId);
            if(worker == null){
                return new ResponseDto(false,3041,"해당하는 근무지, 근로자 정보가 없음",null);
            }
            // 서류 등록 성공
            Document document = new Document(worker.getUser(), worker.getWorkPlace(), requestDto.getType(), requestDto.getExpireDate(), worker, requestDto.getImage());
            Document theDocument = documentRepository.save(document);
            List<ResultDto> resultDto = List.of(new ResultDto(theDocument.getDocumentId(), theDocument.getType(), theDocument.getImageUrl(), theDocument.getExpireDate()));
            return new ResponseDto(true,1022,"서류 등록 성공", resultDto);
        }   catch (IllegalArgumentException e){
            System.out.println(e);
            return new ResponseDto(false,4001,e.toString(),null);
        }catch (Exception e) {
            System.out.println(e);
            return new ResponseDto(false,3006,"서류 등록 실패: "+ e.toString(),null);
        }
    }

    public ResponseDto deleteDocument(Long documentId) {
        try {
            // 근무 근로자 조회
            Optional<Document> document = documentRepository.findById(documentId);
            // 이미지 삭제
            if(document.isEmpty()){
                return new ResponseDto(false,3042,"해당하는 문서가 존재하지 않음",null);
            }
            Document document1 = document.get();
            // 서류 삭제 성공
            documentRepository.delete(document1);
            return new ResponseDto(true,1023,"서류 삭제 성공",null);
        }   catch (IllegalArgumentException e){
            System.out.println(e);
            return new ResponseDto(false, 4002, e.toString(),null);
        }   catch (Exception e) {
            System.out.println(e);
            return new ResponseDto(false,3007,"서류 삭제 실패: " + e.toString(),null);
        }
    }

    public ResponseDto putDocument(Long documentId, RequestDto requestDto) {
        try {
            // 근무 근로자 조회
            Optional<Document> document = documentRepository.findById(documentId);
            if(document.isEmpty()){
                return new ResponseDto(false,3042,"해당하는 문서가 존재하지 않음.",null);
            }
            Document document1 = document.get();
            // 서류 수정 성공
            Document document2 = new Document(document1.getDocumentId(),document1.getUser(),
                    document1.getWorkPlace(),document1.getWorker(),requestDto.getType(),requestDto.getImage(),requestDto.getExpireDate());
            Document document3 = documentRepository.save(document2);
            List<ResultDto> resultDto = List.of(new ResultDto(document3.getDocumentId(), document3.getType(), document3.getImageUrl(), document3.getExpireDate()));
            return new ResponseDto(true,1024,"서류 수정 성공", resultDto);
        }   catch (IllegalArgumentException e){
            System.out.println(e);
            return new ResponseDto(false,4003,"이미지 수정 실패: "+e.toString(),null);
        }   catch (Exception e) {
            System.out.println(e);
            return new ResponseDto(false,3008,"서류 수정 실패: " + e.toString(),null);
        }
    }


}

