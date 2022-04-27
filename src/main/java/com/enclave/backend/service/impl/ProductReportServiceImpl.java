package com.enclave.backend.service.impl;

import com.enclave.backend.repository.ProductReportRepository;
import com.enclave.backend.service.ProductReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.enclave.backend.entity.DateUtil.*;

@Service
public class ProductReportServiceImpl implements ProductReportService {
    @Autowired
    ProductReportRepository productReportRepository;


    @Override
    public List<Object[]> getProductByType(short categoryId, String date, String type) {
        if (categoryId == 0){
            return getAllCategory(date, type);
        }
        return getEachCategory(categoryId, date, type);
    }

    @Override
    public List<Object[]> getAllCategory(String date, String type) {
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
            queryResult = productReportRepository.getAllCategory(startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getEachCategory(short categoryId, String date, String type) {
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
            queryResult = productReportRepository.getEachCategory(categoryId, startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    // EachBranch
    @Override
    public List<Object[]> getByTypeEachBranch(short branchId, short categoryId, String date, String type) {
        if (categoryId == 0){
            return getAllCategoryEachBranch(branchId, date, type);
        }
        return getByCategoryEachBranch(branchId, categoryId, date, type);
    }

    @Override
    public List<Object[]> getAllCategoryEachBranch(short branchId, String date, String type) {
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
            queryResult = productReportRepository.getProductsAllCategoryEachBranch(branchId, startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getByCategoryEachBranch(short branchId, short categoryId, String date, String type) {
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
            queryResult = productReportRepository.getProductsByCategoryEachBranch(branchId, categoryId, startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }
}
