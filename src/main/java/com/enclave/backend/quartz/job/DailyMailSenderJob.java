package com.enclave.backend.quartz.job;

import com.enclave.backend.entity.EmailSending;
import com.enclave.backend.entity.Employee;
import com.enclave.backend.entity.report.EmployeeReport;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private EmailSendingRepository emailSendingRepository;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        List <Employee> employeeList = employeeService.getEmployeeHasRoleManager();
        employeeList.forEach(employee -> {
            Optional<EmailSending> emailSending = emailSendingRepository.findById(employee.getEmail());

            if(emailSending.isPresent()){
                EmailSending sending = emailSending.get();
                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String lastSent = new SimpleDateFormat("yyyy-MM-dd").format(sending.getLastSent());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                log.info(sending.getTimeSent().toString());
                log.info(LocalTime.now().format(dtf));
                log.info(String.valueOf(sending.getTimeSent().toString().equals(LocalTime.now().format(dtf))));
                if(!lastSent.equals(currentDate)){
                    try {
                        emailService.sendEmail(employee.getEmail(), "Daily Report","Daily Report " , getDailyReport(employee));
                        sending.setLastSent(new Date());
                        emailSendingRepository.save(sending);
                        log.info("Send email to manager");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    log.error("Email has been already sent before!");
                }
            } else {
                log.error("Can't send mail!");
            }
        });
    }

    public File[] getDailyReport(Employee employee) throws JRException, IOException, ParseException {
        String date = String.valueOf(LocalDate.now().minusDays(1));
        short branchId = employee.getBranch().getId();

        List<EmployeeReport> employeeReports = reportService.getEmployeesForBranch(branchId, date, "Daily");
        List<ProductReport> productReports = reportService.getProductsByCategoryForBranch(branchId, (short) 0, date, "Daily");

        File employeeReport = reportService.getFilePDF(employeeReports, ReportType.Employee, "Daily", date);
        File productReport = reportService.getFilePDF(productReports, ReportType.Product, "Daily", date);

        return new File[]{employeeReport, productReport};
    }

}
