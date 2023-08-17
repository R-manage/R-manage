package com.rmanage.rmanage.WorkAllowance;

import com.rmanage.rmanage.entity.WorkAllowance;
import com.rmanage.rmanage.entity.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class WorkAllowanceService {


    private final WorkAllowanceRepository workAllowanceRepository;

    public Stat getStat(Worker worker) {

        List<WorkAllowance> workAllowances = findWorkAllowancesByMonth(worker);

        long totalWorkTime = getTotalWorkTime(workAllowances);
        long totalPay = (totalWorkTime / (long) 60) * (worker.getHourlyWage());
        List<DateStat> dateStats = getPayByDate(workAllowances);

        return Stat.builder()
                .totalWorkTime(totalWorkTime)
                .totalPay(totalPay)
                .dateStats(dateStats)
                .build();
    }

    private long getTotalWorkTime(List<WorkAllowance> workAllowances) {

        long sum = 0;
        for (WorkAllowance workAllowance : workAllowances) {
            long minutes = Duration.between(workAllowance.getStartTime(), workAllowance.getEndTime()).toMinutes();
            sum += minutes;
        }

        return workAllowances.stream().mapToLong(workAllowance -> Duration.between(workAllowance.getStartTime(), workAllowance.getEndTime()).toMinutes()).sum();
    }

    private List<DateStat> getPayByDate(List<WorkAllowance> workAllowances) {

         return workAllowances.stream().map(
                 workAllowance -> new DateStat(workAllowance.getWorkDate(), Duration.between(workAllowance.getStartTime(), workAllowance.getEndTime()).toMinutes())).toList();
    }

    private long getHolidayPay(List<WorkAllowance> workAllowances) {

        LocalDate start = LocalDate.now().withDayOfMonth(1);
        Stream<LocalDate> firstWeek = start.datesUntil(start.plusDays(7));

    }

    private List<WorkAllowance> findWorkAllowancesByMonth(Worker worker) {

        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        return workAllowanceRepository.findByWorkerAndWorkDateBetween(worker, start, end);
    }







}


