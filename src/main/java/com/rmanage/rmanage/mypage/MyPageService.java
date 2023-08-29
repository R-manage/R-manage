package com.rmanage.rmanage.mypage;

import com.rmanage.rmanage.WorkAllowance.DateStat;
import com.rmanage.rmanage.WorkAllowance.HolidayStat;
import com.rmanage.rmanage.WorkAllowance.Stat;
import com.rmanage.rmanage.WorkAllowance.WorkAllowanceRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkAllowance;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.mypage.dto.AdminMyPageDto;
import com.rmanage.rmanage.mypage.dto.AdminMyPageResponseDto;
import com.rmanage.rmanage.mypage.dto.WorkerMyPageDto;
import com.rmanage.rmanage.mypage.dto.WorkerMyPageResponseDto;
import com.rmanage.rmanage.usermanage.dto.UserManageResponseDto;
import jakarta.persistence.EntityManager;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageRepository myPageRepository;
    private WorkAllowanceRepository workAllowanceRepository;

    private EntityManager entityManager;

    @Autowired
    public MyPageService(MyPageRepository myPageRepository, EntityManager entityManager){
        this.myPageRepository = myPageRepository;
        this.entityManager = entityManager;
    }

    //근로자_마이페이지 조회
    public WorkerMyPageResponseDto getWorkerMyPage(Long workerId){
        try{
            Worker worker = myPageRepository.findWorkerByWorkerId(workerId);
            if (worker == null){
                return new WorkerMyPageResponseDto(false, 3002, "존재하지 않는 회원", null);
            }
            return new WorkerMyPageResponseDto(true, 1043, "근무자 마이페이지 조회 성공", getWorkerTotalInfo(worker));
        }
        catch(Exception e){
            System.out.println(e);
            return new WorkerMyPageResponseDto(false, 3053, "근무자 마이페이지 조회 실패", null);
        }
    }

    //사장_마이페이지 조회
    public AdminMyPageResponseDto getAdminMyPage(Long userId){
        try{
            User admin = entityManager.find(User.class,userId);

            if (admin == null){
                return new AdminMyPageResponseDto(false, 3002, "존재하지 않는 회원", null);
            }

            if (admin.isEmployee()){
                return new AdminMyPageResponseDto(false, 3068, "ROLE_ADMIN이 아님", null);
            }
            else {
                return new AdminMyPageResponseDto(true, 1041, "사장님 마이페이지 조회 성공", getAdminTotalInfo(userId));
            }
        }
        catch(Exception e){
            System.out.println(e);
            return new AdminMyPageResponseDto(false, 3051, "사장님 마이페이지 조회 실패", null);
        }
    }

    //근무자_근무 시작일, 마지막일 구하기
    private List<WorkAllowance> findWorkAllowancesByMonth(Worker worker) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());
        return workAllowanceRepository.findByWorkerAndWorkDateBetween(worker, start, end);
    }

    //근무자_총일한시간, 총페이, 이번달 일한시간, 이번달 급여 get
    public WorkerMyPageDto getWorkerTotalInfo(Worker worker) {

        List<WorkAllowance> workAllowances = findWorkAllowancesByMonth(worker);
        //이건 매 작업 할당량들은 정해놓은거인듯.해당 worker의 해당 달 시작일, 마지막 일, 구해서 할당량 레포지토리 저장

        User user = myPageRepository.findUserByWorkerId(worker.getWorkerId());
        List<WorkPlace> workPlaces = new ArrayList<>();
        workPlaces.add(worker.getWorkPlace());


        long totalWorkTime = getTotalWorkTime(workAllowances);
        long totalPay = totalWorkTime * worker.getHourlyWage();
        long currentMonthWorkTime = getWorkTimeForCurrentMonth(workAllowances);
        long currentMonthTotalPay = currentMonthWorkTime * worker.getHourlyWage();

        return WorkerMyPageDto.builder()
                .image(user.getImage())
                .nickname(worker.getName())
                .workPlaces(workPlaces)
                .totalWorkTime(totalWorkTime)
                .totalPay(totalPay)
                .currentMonthWorkTime(currentMonthWorkTime)
                .currentMonthTotalPay(currentMonthTotalPay)
                .build();
    }

    //근무자_총 근무시간
    private long getTotalWorkTime(List<WorkAllowance> workAllowances) {
            return workAllowances.stream().mapToLong(workAllowance -> Duration.between(workAllowance.getStartTime(), workAllowance.getEndTime()).toMinutes()).sum() / 60;
    }

    //근무자_현재 근로중인 월의 근무시간
    private long getWorkTimeForCurrentMonth(List<WorkAllowance> workAllowances) {
        Month currentMonth = LocalDate.now().getMonth();

        List<WorkAllowance> currentMonthWorkAllowances = workAllowances.stream()
                .filter(wa -> wa.getWorkDate().getMonth() == currentMonth)
                .collect(Collectors.toList());

        return getTotalWorkTime(currentMonthWorkAllowances);
    }

    //사장님_고용자 수, 고용자들 총 지급금액, 이번달 고용자들 총 지급금액 get
    public AdminMyPageDto getAdminTotalInfo(long userId) {
        User adminUser = entityManager.find(User.class, userId);
        WorkPlace admin = entityManager.find(WorkPlace.class,userId);
        List<Worker> workers = getWorkersByAdminCode(admin.getAdminCode());
        int employeeNum = getWorkersByAdminCode(admin.getAdminCode()).size();
        long totalPay = getWorkersTotalPay(workers);
        long currentMonthTotalPay = getWorkersTotalPayForCurrentMonth(workers);

        return AdminMyPageDto.builder()
                .image(adminUser.getImage())
                .nickname(adminUser.getNickname())
                .employeeNum(employeeNum)
                .totalPay(totalPay)
                .currentMonthTotalPay(currentMonthTotalPay)
                .build();
    }

    //adminCode가 같은 worker들 list
    private List<Worker> getWorkersByAdminCode(String adminCode) {
        List<Worker> workers = myPageRepository.findWorkerByWorkPlace(entityManager.find(WorkPlace.class, adminCode));
        return workers;
    }

    //사장님_고용자 수: getWorkersByAdminCode().size()

    //사장님_고용자들 총 지급금액
    private long getWorkersTotalPay(List<Worker> workers) {
        return workers.stream()
                .mapToLong(worker -> getWorkerTotalInfo(worker).getTotalPay())
                .sum();
    }

    //사장님_이번달 고용자들 총 지급금액
    private long getWorkersTotalPayForCurrentMonth(List<Worker> workers) {
        return workers.stream()
                .mapToLong(worker -> {
                    List<WorkAllowance> workAllowances = workAllowanceRepository.findWorkAllowancesByWorker(worker);
                    long currentMonthWorkTime = getWorkTimeForCurrentMonth(workAllowances);
                    return currentMonthWorkTime * worker.getHourlyWage();
                })
                .sum();
    }

    //이미지 수정 성공여부
    protected UserManageResponseDto updateProfile(long userId, String image){
        try {
            User user = entityManager.find(User.class,userId);
            if (user == null) {
                return new UserManageResponseDto(false, 3002, "존재하지 않는 회원");
            }
            user.updateImage(image);
            return new UserManageResponseDto(true, 1042, "프로필 이미지 수정 성공");
        }   catch (Exception e){
            System.out.println(e);
            return new UserManageResponseDto(false,3052,"프로필 이미지 수정 실패");
        }
    }


}
