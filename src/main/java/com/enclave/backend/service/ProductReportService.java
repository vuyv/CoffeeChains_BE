package com.enclave.backend.service;

import java.util.List;

public interface ProductReportService {
    List<Object[]> getProductByType(short categoryId, String date, String type);

    List<Object[]> getAllCategory(String date, String type);

    List<Object[]> getEachCategory(short categoryId, String date, String type);
}
