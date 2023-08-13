package com.rmanage.rmanage.worker;

import jakarta.validation.Valid;
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

    @PatchMapping("/{workerId}")
    public ResponseEntity update(@PathVariable Long workerId, @Valid @RequestBody WorkerUpdateRequestDto workerUpdateRequestDto) {
        workerService.update(workerId, workerUpdateRequestDto);

        return ResponseEntity.ok("근무지가 수정되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity delete(@PathVariable Long workerId) {
        workerService.delete(workerId);

        return ResponseEntity.ok("근무지가 삭제되었습니다.");
    }


    //세부조회
    @GetMapping("/{workerId}")
    public ResponseEntity<WorkerResponseDto> getWorkerById(@PathVariable Long workerId) {

        return ResponseEntity.ok(workerService.getWorkerById(workerId));

    }

    //전체조회
    @GetMapping()
    public ResponseEntity<List<WorkerResponseDto>> getWorkersByUser(@RequestParam Long userId) {

        return ResponseEntity.ok(workerService.getWorkersByUser(userId));
    }


}
