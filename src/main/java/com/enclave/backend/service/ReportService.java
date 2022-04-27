package com.enclave.backend.service;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface ReportService {

    //MANAGER REPORT

    //allCategory
    List<Object[]> getProductsAllCategoryEachBranch(short branchId, String date, String type);

    //eachCategory
    List<Object[]> getProductsByCategoryEachBranch(short branchId, short categoryId, String date, String type);

    // Seller
    List<Object[]> getEmployeesEachBranch(short branchId, String date, String type);
}
