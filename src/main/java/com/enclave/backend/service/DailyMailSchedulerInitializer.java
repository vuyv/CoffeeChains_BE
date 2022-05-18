package com.enclave.backend.service;

import com.enclave.backend.quartz.MailScheduler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Slf4j
public class DailyMailSchedulerInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final MailScheduler dailyMailScheduler;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            dailyMailScheduler.start();
            log.info("Successfully initialized daily mail subscription scheduler");
        } catch (SchedulerException e) {
            log.error("Unable to initialize daily mail subscription scheduler: {}", e);
        }
    }

}

