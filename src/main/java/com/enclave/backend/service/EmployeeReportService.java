package com.enclave.backend.service;

import java.util.List;

public interface EmployeeReportService {
    List<Object[]> getEachBranch(short branchId, String date, String type);
}
