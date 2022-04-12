package com.enclave.backend.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

@AllArgsConstructor
@Service
public class DateUtil {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

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

    public Date toDate(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(DEFAULT_ZONE_ID).toInstant());
    }

    public LocalDateTime toLocalDate(final Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    public String toString(final LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}