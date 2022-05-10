package com.enclave.backend.quartz.job;

import com.enclave.backend.entity.Report;
import com.enclave.backend.entity.RevenueReport;
import com.enclave.backend.mail.EmailService;
import com.enclave.backend.service.EmployeeService;
import com.enclave.backend.service.report.ReportService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class DailyMailSenderJob implements Job {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmployeeService employeeService;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        final var email = jobExecutionContext.getTrigger().getKey().getName();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd" );
        String date = formatter.format(new Date());

        List<RevenueReport> revenueReports = reportService.getRevenueAllBranch( date,"Daily");
        List<Report> productReports = reportService.getProductAllBranch((short) 0,date,"Daily");

        File revenue = reportService.getFilePDF(revenueReports, "Revenue", "Daily", date);
        File product = reportService.getFilePDF(productReports,"Product","Daily", date);

        File[] attachments = new File[] { revenue , product};
        emailService.sendDailyEmail(email, "Send Email","",attachments);
    }
}
