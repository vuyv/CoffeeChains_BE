package com.enclave.backend.quartz.trigger;

import com.enclave.backend.quartz.job.detail.DailySenderJobDetail;
import lombok.AllArgsConstructor;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DailyMailTriggerFactory {
    private final DailySenderJobDetail dailySenderJobDetail;

    public Trigger generateTrigger(final String email) {
        final var now = LocalDateTime.now();
        return TriggerBuilder.newTrigger().withIdentity(email)
                .forJob(dailySenderJobDetail.getJobDetail())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInHours(1))
                .usingJobData("email", email).build();
    }
}
