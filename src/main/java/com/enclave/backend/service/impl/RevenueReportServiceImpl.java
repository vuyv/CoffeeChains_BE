package com.enclave.backend.service.impl;

import com.enclave.backend.repository.RevenueReportRepository;
import com.enclave.backend.service.RevenueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.enclave.backend.entity.DateUtil.*;

@Service
public class RevenueReportServiceImpl implements RevenueReportService {

    @Autowired
    RevenueReportRepository revenueReportRepository;
    @Override
    public List<Object[]> getAllBranch(String date, String type) {
        List<Object[]> queryResult = new ArrayList<Object[]>();
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
            queryResult = revenueReportRepository.getAllBranch(startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }
}
