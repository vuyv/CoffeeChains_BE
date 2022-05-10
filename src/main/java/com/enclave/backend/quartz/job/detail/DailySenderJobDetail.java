package com.enclave.backend.quartz.job.detail;

import com.enclave.backend.quartz.job.DailyMailSenderJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

@Component
public class DailySenderJobDetail {
    private static final JobDetail jobDetails = JobBuilder.newJob(DailyMailSenderJob.class).withIdentity("daily-mail-sender-job", "DEFAULT").storeDurably().build();
    public JobDetail getJobDetail() {
        return jobDetails;
    }
}
