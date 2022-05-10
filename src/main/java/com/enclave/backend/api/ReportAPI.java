package com.enclave.backend.api;

import com.enclave.backend.service.EmployeeReportService;
import com.enclave.backend.service.ProductReportService;
import com.enclave.backend.service.RevenueReportService;
import com.enclave.backend.service.report.ExportType;
import com.enclave.backend.service.report.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportAPI {

    @Autowired
    private RevenueReportService revenueReportService;

    @Autowired
    private ProductReportService productReportService;

    @Autowired
    private EmployeeReportService employeeReportService;

    @Autowired
    private ReportService reportService;

    //OWNER
    @GetMapping("/owner")
    public List<Object[]> getReportByTime(@RequestParam(name="type") String type, @RequestParam(name="categoryId") short categoryId, @RequestParam(name="timeRange") String timeRange,@RequestParam(name="date") String date ) {
        if (type.equals("Product")) {
            return productReportService.getProductByType(categoryId, date, timeRange);
        }
        return revenueReportService.getAllBranch(date, timeRange);

    }

    @GetMapping("/manager")
    public List<Object[]> getReportByBranch(@RequestParam(name="type") String type, @RequestParam(name="branchId") short branchId, @RequestParam(name="categoryId") short categoryId, @RequestParam(name="timeRange") String timeRange,@RequestParam(name="date") String date ) {
        if (type.equals("Product")) {
            return productReportService.getByTypeEachBranch(branchId,categoryId, date, timeRange);
        }
        return employeeReportService.getEachBranch(branchId,date, timeRange);
    }

    @GetMapping(value = "manager/export")
    ResponseEntity<Void> generateReportEachBranch(@RequestParam(value = "exportType") ExportType exportType, HttpServletResponse response, @RequestParam("branchId") short branchId, @RequestParam("date") String date,
                                                   @RequestParam("timeRange")String timeRange, @RequestParam("type") String type, @RequestParam("categoryId") short categoryId) throws JRException, IOException, ParseException, PrinterException {
        reportService.getReportEachBranch( exportType,  response,type, branchId,  date,  timeRange, categoryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/owner/export")
    ResponseEntity<Void> generateReportOwner(@RequestParam(value = "exportType") ExportType exportType, HttpServletResponse response, @RequestParam("date") String date,
                                        @RequestParam("timeRange")String timeRange, @RequestParam("type") String type, @RequestParam("categoryId") short categoryId) throws JRException, IOException, ParseException, PrinterException {
        reportService.getReportAllBranch( exportType,  response,type,  date,  timeRange, categoryId);
        return ResponseEntity.ok().build();
    }
}
