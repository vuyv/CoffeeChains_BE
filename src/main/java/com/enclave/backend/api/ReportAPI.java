package com.enclave.backend.api;

import com.enclave.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportAPI {
    @Autowired
    private ReportService reportService;

    @GetMapping("/seller/{branchId}/daily/{date}")
    public List<Object[]> getTopSellerDaily(@PathVariable("branchId") short branchId, @PathVariable("date") String date) {
        return reportService.getTopSellerDaily(branchId, date);
    }

    @GetMapping("/seller/{branchId}/{type}/{date}")
    public List<Object[]> getTopSeller(@PathVariable("branchId") short branchId, @PathVariable("date") String date, @PathVariable("type") String type) {
        return reportService.getTopSeller(branchId, date, type);
    }

    @GetMapping("/product/all/{branchId}/{type}/{date}")
    public List<Object[]> getBestSellingAllCategory(@PathVariable("branchId") short branchId, @PathVariable("date") String date, @PathVariable("type") String type) {
        return reportService.getBestSellingAllCategory(branchId, date, type);
    }

    @GetMapping("/product/all/{branchId}/daily/{date}")
    public List<Object[]> getBestSellingAllCategoryDaily(@PathVariable("branchId") short branchId, @PathVariable("date") String date) {
        return reportService.getBestSellingAllCategoryDaily(branchId, date);
    }

    @GetMapping("/product/{categoryId}/{branchId}/{type}/{date}")
    public List<Object[]> getBestSellingByCategory(@PathVariable("branchId") short branchId,@PathVariable("categoryId") short categoryId, @PathVariable("date") String date, @PathVariable("type") String type) {
        return reportService.getBestSellingByCategory(branchId,categoryId, date, type);
    }

    @GetMapping("/product/{categoryId}/{branchId}/daily/{date}")
    public List<Object[]> getBestSellingByCategoryDaily(@PathVariable("branchId") short branchId,@PathVariable("categoryId") short categoryId, @PathVariable("date") String date) {
        return reportService.getBestSellingByCategoryDaily(branchId,categoryId, date);
    }

    //OWNER
    //SUPER CLEAR & CLEAN
    @GetMapping("/owner")
    public List<Object[]> getReportByTime(@RequestParam(name="type") String type, @RequestParam(name="category") short category, @RequestParam(name="timeRange") String timeRange,@RequestParam(name="date") String date ) {
        if (type.equals("Product") && category == 0) {
            return reportService.getProductAllCategoryByTime(date, timeRange);
        }
        if (type.equals("Product") && category != 0) {
            return reportService.getProductEachCategoryByTime(category, date, timeRange);
        }
        return reportService.getRevenueAllBranchByTime(date, timeRange);

    }
}
