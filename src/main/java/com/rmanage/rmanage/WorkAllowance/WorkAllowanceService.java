package com.rmanage.rmanage.WorkAllowance;

import com.rmanage.rmanage.entity.WorkAllowance;
import com.rmanage.rmanage.entity.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WorkAllowanceService {


    private final WorkAllowanceRepository workAllowanceRepository;

    public Stat getStat(Worker worker) {

        List<WorkAllowance> workAllowances = findWorkAllowancesByMonth(worker);

        long totalWorkTime = getTotalWorkTime(workAllowances);
        long totalPay = totalWorkTime * worker.getHourlyWage();
        List<DateStat> dateStats = getPayByDate(workAllowances);
        List<HolidayStat> holidayStats = getHolidayPay(dateStats, worker.getHourlyWage());

        return Stat.builder()
                .totalWorkTime(totalWorkTime)
                .totalPay(totalPay)
                .dateStats(dateStats)
                .holidayStats(holidayStats)
                .build();
    }

    private long getTotalWorkTime(List<WorkAllowance> workAllowances) {

        return workAllowances.stream().mapToLong(workAllowance -> Duration.between(workAllowance.getStartTime(), workAllowance.getEndTime()).toMinutes()).sum() / 60;
    }

    private List<DateStat> getPayByDate(List<WorkAllowance> workAllowances) {

         return workAllowances.stream().map(
                 workAllowance -> new DateStat(workAllowance.getWorkDate(), Duration.between(workAllowance.getStartTime(), workAllowance.getEndTime()).toMinutes() / 60)).toList();
    }

    private List<HolidayStat> getHolidayPay(List<DateStat> dateStats, long hourlyWage) {

        LocalDate now = LocalDate.now();
        int lengthOfMonth = now.lengthOfMonth();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(lengthOfMonth);
        LocalDate week = now.withDayOfMonth(8);

        List<HolidayStat> holidayStats = new ArrayList<>();
        while(week.isBefore(end)) {

            LocalDate finalWeek = week;
            LocalDate finalStart = start;
            long weekWorkTime = dateStats.stream().filter(dateStat -> (dateStat.getDate().isEqual(finalStart) || dateStat.getDate().isAfter(finalStart)) && dateStat.getDate().isBefore(finalWeek)).mapToLong(DateStat::getWorkTime).sum();

            long holidayPay = 0;
            if (weekWorkTime >= 15) {
                holidayPay = hourlyWage * 8;
            }
            holidayStats.add(new HolidayStat(start, week.minusDays(1), holidayPay));

            start = start.plusWeeks(1);
            week = week.plusWeeks(1);
        }

        return holidayStats;
    }

    private List<WorkAllowance> findWorkAllowancesByMonth(Worker worker) {

        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        return workAllowanceRepository.findByWorkerAndWorkDateBetween(worker, start, end);
    }







}


