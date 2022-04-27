package com.enclave.backend.service;

import java.util.List;

public interface ReportService {

    //OWNER REPORT
    //revenue
    List<Object[]> getRevenueAllBranchByTime(String date, String type);

    //allCategory
    List<Object[]> getProductAllCategoryByTime(String date, String type);

    //eachCategory
    List<Object[]> getProductEachCategoryByTime(short categoryId, String date, String type);

    //MANAGER REPORT

    //allCategory
    List<Object[]> getProductsAllCategoryEachBranch(short branchId, String date, String type);

    //eachCategory
    List<Object[]> getProductsByCategoryEachBranch(short branchId, short categoryId, String date, String type);

    // Seller
    List<Object[]> getEmployeesEachBranch(short branchId, String date, String type);
}
