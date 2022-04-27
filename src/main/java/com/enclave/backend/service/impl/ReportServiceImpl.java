package com.enclave.backend.service.impl;

import com.enclave.backend.entity.DateUtil;
import com.enclave.backend.repository.ReportRepository;
import com.enclave.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.enclave.backend.entity.DateUtil.*;

//@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    // Manager
    @Override
    public List<Object[]> getProductsAllCategoryEachBranch(short branchId, String date, String type) {
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
            queryResult = reportRepository.getProductsAllCategoryEachBranch(branchId, startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getProductsByCategoryEachBranch(short branchId, short categoryId, String date, String type) {
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
            queryResult = reportRepository.getProductsByCategoryEachBranch(branchId, categoryId, startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getEmployeesEachBranch(short branchId, String date, String type) {
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
            queryResult = reportRepository.getEmployeesEachBranch(branchId, startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }
}
