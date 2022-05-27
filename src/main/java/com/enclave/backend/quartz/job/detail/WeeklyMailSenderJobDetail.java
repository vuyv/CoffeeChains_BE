package com.enclave.backend.quartz.job.detail;

import com.enclave.backend.quartz.job.WeeklyMailSenderJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

@Component
public class WeeklyMailSenderJobDetail {
    private static final JobDetail jobDetails = JobBuilder.newJob(WeeklyMailSenderJob.class)
            .withIdentity("weekly-mail-sender-job", "DEFAULT").storeDurably().build();
    public JobDetail getJobDetail() {
        return jobDetails;
    }
}
