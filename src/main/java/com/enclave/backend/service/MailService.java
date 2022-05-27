package com.enclave.backend.service;

import com.enclave.backend.quartz.MailScheduler;
import com.enclave.backend.quartz.trigger.DailyMailTriggerFactory;
import com.enclave.backend.quartz.trigger.WeeklyMailTriggerFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final WeeklyMailTriggerFactory weeklyMailTriggerFactory;
    private final DailyMailTriggerFactory dailyMailTriggerFactory;
    private final MailScheduler mailScheduler;

    public ResponseEntity<?> createSchedule(
            ) {
//        final var response = new JSONObject();

        final var weeklyTrigger = weeklyMailTriggerFactory
                .generateTrigger();
        mailScheduler.addTrigger(weeklyTrigger);

        final var dailyTrigger = dailyMailTriggerFactory
                .generateTrigger();
        mailScheduler.addTrigger(dailyTrigger);

//        response.put("message", "Successfully send weekly mail");
        return ResponseEntity.ok("");
    }
}
