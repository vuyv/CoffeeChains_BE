package com.enclave.backend.service;

import java.util.List;

public interface RevenueReportService {
    //OWNER REPORT
    //revenue
    List<Object[]> getAllBranch(String date, String type);
}
