package com.enclave.backend.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;

@AllArgsConstructor
@Service
public class DateUtil {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

    public static LocalDateTime startOfDay() {
        return LocalDateTime.now(DEFAULT_ZONE_ID).with(LocalTime.MIN);
    }

    public static LocalDateTime endOfDay() {
        return LocalDateTime.now(DEFAULT_ZONE_ID).with(LocalTime.MAX);
    }

    //note that week starts with Monday
    public static LocalDateTime startOfWeek() {
        return LocalDateTime.now(DEFAULT_ZONE_ID).with(LocalTime.MIN).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    //note that week ends with Sunday
    public static LocalDateTime endOfWeek() {
        return LocalDateTime.now(DEFAULT_ZONE_ID).with(LocalTime.MAX).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    public static long toMills(final LocalDateTime localDateTime) {
        return localDateTime.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDate(final Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    //note that week starts with Monday
    public static LocalDateTime startOfWeek(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        return LocalDateTime.of(localDate, LocalTime.MIN).with(LocalTime.MIN).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    //note that week ends with Sunday
    public static LocalDateTime endOfWeek(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        return LocalDateTime.of(localDate, LocalTime.MAX).with(LocalTime.MAX).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    public static LocalDateTime startOfMonth(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        return LocalDateTime.of(localDate, LocalTime.MIN).with(LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDateTime endOfMonth(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        return LocalDateTime.of(localDate, LocalTime.MAX).with(LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDateTime startOfDay(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static LocalDateTime endOfDay(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }
    public static LocalDateTime last7days(Date date){
        LocalDate localDate = LocalDate.from(toLocalDate(date)).minusDays(6);
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    //convert String to Date
    public static Date StringtoDate(String dateString) {
        Date dateResult = new Date();
        try {
            dateResult = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            System.out.println(e);
        }
        return dateResult;
    }

    public boolean belongsToCurrentDay(final LocalDateTime localDateTime) {
        return localDateTime.isAfter(startOfDay()) && localDateTime.isBefore(endOfDay());
    }

    public boolean belongsToCurrentWeek(final LocalDateTime localDateTime) {
        return localDateTime.isAfter(startOfWeek()) && localDateTime.isBefore(endOfWeek());
    }

    public LocalDateTime startOfMonth() {
        return LocalDateTime.now(DEFAULT_ZONE_ID).with(LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDateTime endOfMonth() {
        return LocalDateTime.now(DEFAULT_ZONE_ID).with(LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth());
    }

    public boolean belongsToCurrentMonth(final LocalDateTime localDateTime) {
        return localDateTime.isAfter(startOfMonth()) && localDateTime.isBefore(endOfMonth());
    }

    public static Date toDate(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(DEFAULT_ZONE_ID).toInstant());
    }

    public static String toString(final LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static LocalDateTime startOfQuarter(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        LocalDate firstDayOfQuarter = localDate.with(localDate.getMonth().firstMonthOfQuarter()).with(TemporalAdjusters.firstDayOfMonth());
        return LocalDateTime.of(firstDayOfQuarter,  LocalTime.MIN);
    }

    public static LocalDateTime endOfQuarter(Date date) {
        LocalDate firstDayOfQuarter = LocalDate.from(startOfQuarter(date));
        LocalDate lastDayOfQuarter = firstDayOfQuarter.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
        return LocalDateTime.of(lastDayOfQuarter,  LocalTime.MAX);
    }

    public static LocalDateTime startOfPreviousQuarter(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        int year = localDate.getYear();
        int quarter = localDate.get(QUARTER_OF_YEAR);

        LocalDate firstDayOfQuarter = YearMonth.of(year, 1)
                .with(QUARTER_OF_YEAR, quarter-1)
                .atDay(1);
        return LocalDateTime.of(firstDayOfQuarter,  LocalTime.MIN);
    }

    public static LocalDateTime endOfPreviousQuarter(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        int year = localDate.getYear();
        int quarter = localDate.get(QUARTER_OF_YEAR);
        LocalDate lastDayOfQuarter = YearMonth.of(year, 3)
                .with(QUARTER_OF_YEAR, quarter-1)
                .atEndOfMonth();
        return LocalDateTime.of(lastDayOfQuarter,  LocalTime.MAX);
    }

    public static LocalDateTime startOfLastMonth(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        LocalDate firstDay = YearMonth.of(localDate.getYear(), localDate.getMonth().minus(1))
                .atDay(1);
        return LocalDateTime.of(firstDay,  LocalTime.MIN);
    }

    public static LocalDateTime endOfLastMonth(Date date) {
        LocalDate localDate = LocalDate.from(toLocalDate(date));
        LocalDate lastDay = YearMonth.of(localDate.getYear(), localDate.getMonth().minus(1))
                .atEndOfMonth();
        return LocalDateTime.of(lastDay,  LocalTime.MAX);
    }
}