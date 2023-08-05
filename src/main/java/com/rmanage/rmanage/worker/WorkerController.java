package com.rmanage.rmanage.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{workerId}")
    public ResponseEntity<WorkerResponseDto> getWorker(@PathVariable Long workerId) {

        return ResponseEntity.ok(workerService.getWorkerById(workerId));

    }

    @GetMapping()
    public ResponseEntity<List<WorkerResponseDto>> getWorkers(@RequestParam Long userId) {

        return ResponseEntity.ok(workerService.getWorkerByUser(userId));
    }


}
