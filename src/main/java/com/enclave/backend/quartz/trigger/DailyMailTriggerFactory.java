package com.enclave.backend.quartz.trigger;

import com.enclave.backend.quartz.job.detail.DailySenderJobDetail;
import com.enclave.backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DailyMailTriggerFactory {
    private final DailySenderJobDetail dailySenderJobDetail;
    @Autowired
    private EmployeeService employeeService;

    public Trigger generateTrigger() {
//        Employee employee= employeeService.getEmployeeHasRoleOwner();
//        String email = employee.getEmail();
        return TriggerBuilder.newTrigger().withIdentity("email-manager")
                .forJob(dailySenderJobDetail.getJobDetail())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMinutes(2))
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .repeatForever()
//                        .withIntervalInMinutes(2)
//                        .withMisfireHandlingInstructionFireNow())
                .usingJobData("email-manager", "sent-email-to-manager").build();
    }
}
