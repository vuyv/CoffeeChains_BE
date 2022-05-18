package com.enclave.backend.quartz;

import com.enclave.backend.quartz.job.detail.DailySenderJobDetail;
import com.enclave.backend.quartz.job.detail.WeeklyMailSenderJobDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MailScheduler {
    private final Scheduler scheduler;
    private final DailySenderJobDetail dailySenderJobDetail;
    private final WeeklyMailSenderJobDetail weeklyMailSenderJobDetail;

    public void start() throws SchedulerException {
        this.scheduler.start();
        this.scheduler.addJob(dailySenderJobDetail.getJobDetail(), false);
        this.scheduler.addJob(weeklyMailSenderJobDetail.getJobDetail(), false);
    }

    public void addTrigger(Trigger trigger) {
        try {
            this.scheduler.scheduleJob(trigger);
            log.info("Successfully scheduled trigger with identity: {}", trigger.getKey());
        } catch (ObjectAlreadyExistsException exception) {
            log.error("Daily mail sender Trigger Already Added!");
            throw new RuntimeException();
        } catch (SchedulerException e) {
            log.error("Unable to add trigger {}", e);
            throw new RuntimeException();
        }
    }

    public void removeTrigger(final String email) {
        try {
            this.scheduler.unscheduleJob(new TriggerKey(email));
        } catch (SchedulerException e) {
            log.error("Unable to unschedule email from daily mail subscription service: {}", e);
            throw new RuntimeException();
        }
    }

    public Boolean triggerWithEmailScheduled(final String email) {
        try {
            return this.scheduler.checkExists(new TriggerKey(email));
        } catch (SchedulerException e) {
            log.error("Unable to check for trigger existence: {}", e);
            throw new RuntimeException();
        }
    }

}
