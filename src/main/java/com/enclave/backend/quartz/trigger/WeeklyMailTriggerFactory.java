package com.enclave.backend.quartz.trigger;

import com.enclave.backend.quartz.job.detail.WeeklyMailSenderJobDetail;
import lombok.AllArgsConstructor;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Component
@AllArgsConstructor
public class WeeklyMailTriggerFactory {

    @Autowired
    private WeeklyMailSenderJobDetail weeklyMailSenderJobDetail;

    public Trigger generateTrigger() {
        return TriggerBuilder.newTrigger().withIdentity("email-owner")
                .forJob(weeklyMailSenderJobDetail.getJobDetail())
                .withSchedule(cronSchedule("0 5 22 ? * SUN"))
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMinutes(2))
                .usingJobData("email-owner", "send-email-to-owner").build();
    }
}
