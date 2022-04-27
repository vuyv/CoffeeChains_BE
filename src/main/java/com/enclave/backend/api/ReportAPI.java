package com.enclave.backend.api;

import com.enclave.backend.service.ProductReportService;
import com.enclave.backend.service.ReportService;
import com.enclave.backend.service.RevenueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportAPI {
    @Autowired
    private ReportService reportService;

    @Autowired
    private RevenueReportService revenueReportService;

    @Autowired
    private ProductReportService productReportService;

    //OWNER
    @GetMapping("/owner")
    public List<Object[]> getReportByTime(@RequestParam(name="type") String type, @RequestParam(name="categoryId") short categoryId, @RequestParam(name="timeRange") String timeRange,@RequestParam(name="date") String date ) {
        if (type.equals("Product")) {
            return productReportService.getProductByType(categoryId, date, timeRange);
        }
        return revenueReportService.getAllBranch(date, timeRange);

    }

    @GetMapping("/manager")
    public List<Object[]> getReportByBranch(@RequestParam(name="type") String type, @RequestParam(name="branch") short branch, @RequestParam(name="category") short category, @RequestParam(name="timeRange") String timeRange,@RequestParam(name="date") String date ) {
        if (type.equals("Product") && category == 0) {
            return reportService.getProductsAllCategoryEachBranch(branch, date, timeRange);
        }
        if (type.equals("Product") && category != 0) {
            return reportService.getProductsByCategoryEachBranch(branch, category, date, timeRange);
        }
        return reportService.getEmployeesEachBranch(branch,date, timeRange);

    }
}
