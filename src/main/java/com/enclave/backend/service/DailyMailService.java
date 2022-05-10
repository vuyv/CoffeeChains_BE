package com.enclave.backend.service;

import com.enclave.backend.entity.DailyMailCreationRequest;
import com.enclave.backend.mail.EmailService;
import com.enclave.backend.quartz.DailyMailScheduler;
import com.enclave.backend.quartz.trigger.DailyMailTriggerFactory;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DailyMailService {
    private final DailyMailTriggerFactory dailyMailTriggerFactory;
    private final DailyMailScheduler dailyMailScheduler;
    private final EmailService emailService;

    public ResponseEntity<?> subscribe(
            final DailyMailCreationRequest dailyMailCreationRequest) {
        final var response = new JSONObject();
        final var trigger = dailyMailTriggerFactory
                .generateTrigger(dailyMailCreationRequest.getEmail());
        dailyMailScheduler.addTriggerInDailyMailService(trigger);
        emailService.sendEmail(dailyMailCreationRequest.getEmail(),
                "Successfully Subscribed to daily mail subscription service",
                "");
        response.put("message", dailyMailCreationRequest.getEmail()
                + " has been successfully subscribed to daily mail subscription service");
        return ResponseEntity.ok(response.toString());
    }
}
