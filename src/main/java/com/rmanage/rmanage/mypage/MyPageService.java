package com.rmanage.rmanage.mypage;

import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.mypage.dto.AdminMyPageDto;
import com.rmanage.rmanage.mypage.dto.AdminMyPageResponseDto;
import com.rmanage.rmanage.mypage.dto.WorkerMyPageDto;
import com.rmanage.rmanage.mypage.dto.WorkerMyPageResponseDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private EntityManager entityManager;

    @Autowired
    public MyPageService(MyPageRepository myPageRepository, EntityManager entityManager){
        this.myPageRepository = myPageRepository;
        this.entityManager = entityManager;
    }

    //근로자 마이페이지
    public WorkerMyPageResponseDto getWorkerMyPage(Long userId, Long workerId){
        try{
            List<Worker> workers = myPageRepository.findWorkerByWorkerId(workerId);
            List<WorkerMyPageDto> workerMyPageDtoList = new ArrayList<>();
//            for (Worker worker : workers) {
//                workerMyPageDtoList.add(new WorkerMyPageDto(worker.getUser().getNickname(),worker.getWorkPlace(), //총 일한시간, 총페이, 이번달일한시간, 이번달총급여);
//            }
            return new WorkerMyPageResponseDto(true, 1011, "조회 성공", workerMyPageDtoList);
        }
        catch(Exception e){
            System.out.println(e);
            return new WorkerMyPageResponseDto(false, 3011, "조회 실패", null);
        }
    }

    //사장 마이페이지
    public AdminMyPageResponseDto getAdminMyPage(Long userId, boolean isEmployee){
        try{
            List<User> admins = myPageRepository.findUserByUserUserId(userId);
            List<AdminMyPageDto> adminMyPageDtoList = new ArrayList<>();
//            for (User admin : admins) {
//                adminMyPageDtoList.add(new AdminMyPageDto(admin.getNickname(), //근로자 수, 총 지급금액, 이번달 지급금액);
//            }
            return new AdminMyPageResponseDto(true, 1011, "조회 성공", adminMyPageDtoList);
        }
        catch(Exception e){
            System.out.println(e);
            return new AdminMyPageResponseDto(false, 3011, "조회 실패", null);
        }
    }


}
