package com.rmanage.rmanage.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/worker")
public class WorkerController {
    private final WorkerService workerService;

    @PostMapping()
    public ResponseEntity save(@RequestBody WorkerSaveDto workerSaveDto) {
        workerService.save(workerSaveDto);
        return ResponseEntity.ok("근무지가 등록되었습니다");
    }
}
