package com.enclave.backend.service;

import java.util.List;

public interface ReportService {

    List<Object[]> getTopSeller(short branchId,String date, String type);

    List<Object[]> getTopSellerDaily(short branchId,String date);

    List<Object[]> getBestSellingAllCategory(short branchId, String date, String type);

    List<Object[]> getBestSellingAllCategoryDaily(short branchId, String date);

    List<Object[]> getBestSellingByCategory(short branchId, short categoryId, String date, String type);

    List<Object[]> getBestSellingByCategoryDaily(short branchId, short categoryId, String date);
}
