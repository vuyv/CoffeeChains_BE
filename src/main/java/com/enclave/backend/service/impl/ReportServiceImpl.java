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

    @Override
    public List<Object[]> getTopSeller(short branchId, String date, String type) {
        DateUtil dateUtil = new DateUtil();
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if(type.equals("weekly")){
                LocalDateTime startOfWeek = dateUtil.startOfWeek(selectedDate);
                LocalDateTime endOfWeek = endOfWeek((selectedDate));
                queryResult = reportRepository.getTopSeller(branchId, startOfWeek.toString(), endOfWeek.toString());
            }
            if(type.equals("monthly")){
                LocalDateTime startOfMonth = dateUtil.startOfMonth(selectedDate);
                LocalDateTime endOfMonth = dateUtil.endOfMonth((selectedDate));
                queryResult = reportRepository.getTopSeller(branchId, startOfMonth.toString(), endOfMonth.toString());
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getTopSellerDaily(short branchId, String date) {
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            queryResult = reportRepository.getTopSellerDaily(branchId, date);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getBestSellingAllCategory(short branchId, String date, String type) {
        DateUtil dateUtil = new DateUtil();
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if(type.equals("weekly")){
                LocalDateTime startOfWeek = dateUtil.startOfWeek(selectedDate);
                LocalDateTime endOfWeek = endOfWeek((selectedDate));
                queryResult = reportRepository.getBestSellingAllCategory(branchId, startOfWeek.toString(), endOfWeek.toString());
            }
            if(type.equals("monthly")){
                LocalDateTime startOfMonth = dateUtil.startOfMonth(selectedDate);
                LocalDateTime endOfMonth = dateUtil.endOfMonth((selectedDate));
                queryResult = reportRepository.getBestSellingAllCategory(branchId, startOfMonth.toString(), endOfMonth.toString());
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getBestSellingAllCategoryDaily(short branchId, String date) {
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            queryResult = reportRepository.getBestSellingAllCategoryDaily(branchId, date);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getBestSellingByCategory(short branchId,short categoryId, String date, String type) {
        DateUtil dateUtil = new DateUtil();
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if(type.equals("weekly")){
                LocalDateTime startOfWeek = dateUtil.startOfWeek(selectedDate);
                LocalDateTime endOfWeek = endOfWeek((selectedDate));
                queryResult = reportRepository.getBestSellingByCategory(branchId, categoryId, startOfWeek.toString(), endOfWeek.toString());
            }
            if(type.equals("monthly")){
                LocalDateTime startOfMonth = dateUtil.startOfMonth(selectedDate);
                LocalDateTime endOfMonth = dateUtil.endOfMonth((selectedDate));
                queryResult = reportRepository.getBestSellingByCategory(branchId, categoryId, startOfMonth.toString(), endOfMonth.toString());
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getBestSellingByCategoryDaily(short branchId,short categoryId, String date) {
        List<Object[]> queryResult = new ArrayList<Object[]>();
        try {
            queryResult = reportRepository.getBestSellingByCategoryDaily(branchId, categoryId, date);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    //OWNER
    //Revenue
    @Override
    public List<Object[]> getRevenueAllBranchByTime(String date, String type) {
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
            queryResult = reportRepository.getRevenueAllBranchByTime(startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getProductAllCategoryByTime(String date, String type) {
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
            queryResult = reportRepository.getProductAllCategoryByTime(startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }

    @Override
    public List<Object[]> getProductEachCategoryByTime(short categoryId,String date, String type) {
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
            queryResult = reportRepository.getProductEachCategoryByTime(categoryId, startDate, endDate);
        } catch (Exception e){
            System.out.println(e);
        }
        return queryResult;
    }
}
