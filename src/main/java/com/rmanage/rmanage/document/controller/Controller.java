package com.rmanage.rmanage.document.controller;

import com.rmanage.rmanage.document.dto.ResponseDto;
import com.rmanage.rmanage.document.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
public class Controller {

    private DocumentService documentService;

    public Controller(DocumentService theDocumentService){
        this.documentService = theDocumentService;
    }

    @GetMapping("/every/workers/{workerId}/documents")
    public ResponseEntity<ResponseDto> getDocuments(@PathVariable int workerId){
        ResponseDto responseDto = documentService.getDocuments(workerId);
        ResponseEntity<ResponseDto> responseEntity = null;
        if(responseDto.getCode() <= 1999){
            responseEntity = ResponseEntity.ok(responseDto);
        } else{
            responseEntity = new ResponseEntity(responseDto,HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PostMapping("/every/workers/{workerId}/documents")
    public ResponseEntity postDocument(@PathVariable int workerId, @RequestParam(value = "type") String type,
                                       @RequestParam(value = "expireDate") LocalDate expireDate, @RequestParam(value = "image", required = false) MultipartFile image){
        ResponseDto responseDto = documentService.postDocument(workerId, type, expireDate, image);
        ResponseEntity<ResponseDto> responseEntity = null;
        if(responseDto.getCode() <= 1999){
            responseEntity = ResponseEntity.ok(responseDto);
        } else{
            responseEntity = new ResponseEntity(responseDto,HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping("/every/documents/{documentId}")
    public ResponseEntity deleteDocument(@PathVariable int documentId){
        ResponseDto responseDto = documentService.deleteDocument((long)documentId);
        ResponseEntity<ResponseDto> responseEntity = null;
        if(responseDto.getCode() <= 1999){
            responseEntity = ResponseEntity.ok(responseDto);
        } else{
            responseEntity = new ResponseEntity(responseDto,HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PutMapping("/every/documents/{documentId}")
    public ResponseEntity putDocument(@PathVariable int documentId,@RequestParam(value = "type") String type,
                                      @RequestParam(value = "expireDate") LocalDate expireDate, @RequestParam(value = "image", required = false) MultipartFile image){
        ResponseDto responseDto = documentService.putDocument((long)documentId, type, expireDate, image);
        ResponseEntity<ResponseDto> responseEntity = null;
        if(responseDto.getCode() <= 1999){
            responseEntity = ResponseEntity.ok(responseDto);
        } else{
            responseEntity = new ResponseEntity(responseDto,HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
