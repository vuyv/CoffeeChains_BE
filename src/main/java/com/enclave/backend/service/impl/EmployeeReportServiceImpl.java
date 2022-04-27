package com.enclave.backend.service.impl;

import com.enclave.backend.repository.EmployeeReportRepository;
import com.enclave.backend.service.EmployeeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.enclave.backend.entity.DateUtil.*;

@Service
public class EmployeeReportServiceImpl implements EmployeeReportService {

    @Autowired
    private EmployeeReportRepository employeeReportRepository;

    @Override
    public List<Object[]> getEachBranch(short branchId, String date, String type) {
        List<Object[]> queryResult = new ArrayList<>();
        Date selectedDate = StringtoDate(date);
        try {
            String startDate = "";
            String endDate = "";
            if(type.equals("Daily")){
                startDate = startOfDay(selectedDate).toString();
                endDate = endOfDay((selectedDate)).toString();
            }
            if(type.equals("Weekly")){
                startDate = startOfWeek(selectedDate).toString();
                endDate = endOfWeek((selectedDate)).toString();
            }
            if(type.equals("Monthly")){
                startDate = startOfMonth(selectedDate).toString();
                endDate = endOfMonth((selectedDate)).toString();
            }
            queryResult = employeeReportRepository.getEachBranch(branchId, startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }
}
