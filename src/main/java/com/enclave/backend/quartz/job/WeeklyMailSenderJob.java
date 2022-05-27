package com.enclave.backend.quartz.job;

import com.enclave.backend.entity.EmailSending;
import com.enclave.backend.entity.Employee;
import com.enclave.backend.entity.RevenueReport;
import com.enclave.backend.entity.report.ProductReport;
import com.enclave.backend.entity.report.ReportType;
import com.enclave.backend.mail.EmailService;
import com.enclave.backend.repository.EmailSendingRepository;
import com.enclave.backend.service.EmployeeService;
import com.enclave.backend.service.report.ReportService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Component
public class WeeklyMailSenderJob implements Job {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailSendingRepository emailSendingRepository;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Employee employee= employeeService.getEmployeeHasRoleOwner();
        String email = employee.getEmail();
        Optional<EmailSending> emailSending = emailSendingRepository.findById(email);

        if(emailSending.isPresent()){
            EmailSending sending = emailSending.get();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String lastSent = new SimpleDateFormat("yyyy-MM-dd ").format(emailSending.get().getLastSent());

            if(!lastSent.equals(currentDate)){
                emailService.sendEmail(email, "Weekly Report","Weekly Report " , getWeeklyReport());
                sending.setLastSent(new Date());
                emailSendingRepository.save(sending);
                log.info("Send email to owner");
            } else {
                log.error("Email has been already sent before!");
            }
        }
    }

    public File[] getWeeklyReport() throws JRException, IOException, ParseException {
        String date = String.valueOf(LocalDate.now().minusWeeks(1));

        List<RevenueReport> revenueReports = reportService.getRevenueAllBranch(date, "Weekly");
        List<ProductReport> productReports = reportService.getProductsByCategory((short) 0, date, "Weekly");

        File revenue = reportService.getFilePDF(revenueReports, ReportType.Revenue, "Weekly", date);
        File product = reportService.getFilePDF(productReports, ReportType.Product, "Weekly", date);
        return new File[]{revenue, product};
    }
}
