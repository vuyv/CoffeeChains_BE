package com.enclave.backend.service.report;

import com.enclave.backend.entity.Report;
import com.enclave.backend.entity.RevenueReport;
import com.enclave.backend.service.EmployeeReportService;
import com.enclave.backend.service.ProductReportService;
import com.enclave.backend.service.RevenueReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static com.enclave.backend.entity.DateUtil.*;

@Service
public class ReportService {

    private final List<Report> reports = new ArrayList<>();
    private final List<RevenueReport> revenueReports = new ArrayList<>();

    @Autowired
    private EmployeeReportService employeeReportService;

    @Autowired
    private ProductReportService productReportService;

    @Autowired
    private RevenueReportService revenueReportService;

    private List<Object[]> queryResult = new ArrayList<>();

    public List<Report> getProductsEachBranch(short branchId, short categoryId, String date, String timeRange) {
        queryResult = productReportService.getByTypeEachBranch(branchId, categoryId, date, timeRange);
        for (int i = 0; i < queryResult.size(); i++) {
            Report report = new Report();
            report.setName((String) queryResult.get(i)[0]);
            Integer quantity = ((BigDecimal) queryResult.get(i)[1]).intValue();
            report.setQuantity(quantity);
            report.setRevenue((Double) queryResult.get(i)[2]);
            reports.add(report);
        }
        return reports;
    }

    public List<Report> getEmployeesEachBranch(short branchId, String date, String timeRange) {
        queryResult = employeeReportService.getEachBranch(branchId, date, timeRange);
        for (int i = 0; i < queryResult.size(); i++) {
            Report report = new Report();
            report.setId(i);
            report.setName((String) queryResult.get(i)[0]);
            Integer quantity = ((BigInteger) queryResult.get(i)[1]).intValue();
            report.setQuantity(quantity);
            report.setRevenue((Double) queryResult.get(i)[2]);
            reports.add(report);
        }
        return reports;
    }

    public List<Report> getProductAllBranch(short categoryId, String date, String timeRange) {
        queryResult = productReportService.getProductByType(categoryId, date, timeRange);
        for (int i = 0; i < queryResult.size(); i++) {
            Report report = new Report();
            report.setName((String) queryResult.get(i)[0]);
            Integer quantity = ((BigDecimal) queryResult.get(i)[1]).intValue();
            report.setQuantity(quantity);
            report.setRevenue((Double) queryResult.get(i)[2]);
            reports.add(report);
        }
        return reports;
    }

    public List<RevenueReport> getRevenueAllBranch(String date, String timeRange) {
        queryResult = revenueReportService.getAllBranch(date, timeRange);
        for (int i = 0; i < queryResult.size(); i++) {
            RevenueReport report = new RevenueReport();
            report.setId(i);
            report.setBranch((String) queryResult.get(i)[0]);
            report.setAddress((String) queryResult.get(i)[1]);
            Integer quantity = ((BigInteger) queryResult.get(i)[2]).intValue();
            report.setQuantity(quantity);
            report.setRevenue((Double) queryResult.get(i)[3]);
            revenueReports.add(report);
        }
        return revenueReports;
    }

    public void getReportAllBranch(ExportType exportType, HttpServletResponse response, String type, String date, String timeRange, short categoryId) throws JRException, IOException, ParseException, PrinterException {
        List<Report> productReportList = new ArrayList<>();
        List<RevenueReport> revenueReportList = new ArrayList<>();
        if (type.equals("Revenue")) {
            revenueReportList = getRevenueAllBranch(date, timeRange);
            exportReport(revenueReportList, exportType, response, type, timeRange, date);
        } else {
            productReportList = getProductAllBranch(categoryId, date, timeRange);
            exportReport(productReportList, exportType, response, type, timeRange, date);
        }
        productReportList.clear();
        revenueReportList.clear();
    }

    public void getReportEachBranch(ExportType exportType, HttpServletResponse response, String type, short branchId, String date, String timeRange, short categoryId) throws JRException, IOException, ParseException, PrinterException {
        List<Report> reports = new ArrayList<>();

        if (type.equals("Employee")) {
            reports = getEmployeesEachBranch(branchId, date, timeRange);
            exportReport(reports, exportType, response, type, timeRange, date);
        } else {
            reports = getProductsEachBranch(branchId, categoryId, date, timeRange);
            exportReport(reports, exportType, response, type, timeRange, date);
        }
        reports.clear();
    }

    private HashMap<String, Object> setParameters(String type, String timeRange, String dateString) throws ParseException {
        HashMap<String, Object> parameters = new HashMap();
        String title = "";
        if (type.equals("Employee")) {
            title = "Employee Report";
        } else if (type.equals("Revenue")) {
            title = "Revenue Report";
        } else {
            title = "Product Report";
        }
        parameters.put("title", title);
        parameters.put("timeRange", timeRange);

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        if (timeRange.equals("Monthly")) {
            startDate = LocalDate.from(startOfMonth(date));
            endDate = LocalDate.from(endOfMonth(date));
            parameters.put("startDate", startDate);
            parameters.put("endDate", endDate);
        }
        if (timeRange.equals("Weekly")) {
            startDate = LocalDate.from(startOfWeek(date));
            endDate = LocalDate.from(endOfWeek(date));
            parameters.put("startDate", startDate);
            parameters.put("endDate", endDate);
        }
        if (timeRange.equals("Daily")) {
            startDate = LocalDate.from(startOfDay(date));
            endDate = LocalDate.from(endOfDay(date));
            parameters.put("startDate", startDate);
            parameters.put("endDate", endDate);
        }
        return parameters;
    }

    public InputStream getReportTemplate(String type) {
        InputStream reportStream = null;
        if (type.equals("Revenue")) {
            reportStream = getClass().getResourceAsStream("/templates/revenueReport.jrxml");
        } else {
            reportStream = getClass().getResourceAsStream("/templates/report.jrxml");
        }
        return reportStream;
    }

    public void exportReport(Collection<?> beanCollection, ExportType exportType, HttpServletResponse response, String type, String timeRange, String dateString) throws JRException, IOException, ParseException, PrinterException {
//        InputStream reportStream = getClass().getResourceAsStream("/templates/report_" + exportType.toString().toLowerCase() + ".jrxml");

        HashMap<String, Object> parameters = setParameters(type, timeRange, dateString);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanCollection);
        JasperReport jasperReport = JasperCompileManager.compileReport(getReportTemplate(type));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

        var fileName = parameters.get("title");

        if (exportType == ExportType.PDF) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".pdf;");
            exporter.exportReport();

        } else if (exportType == ExportType.HTML) {
            HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
            response.setContentType("text/html");
//            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".html;");
            exporter.exportReport();
        }
    }
    public JasperPrint getJasperPrint(Collection<?> beanCollection, String type, HashMap<String, Object> parameters) throws JRException {
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanCollection);
        JasperReport jasperReport = JasperCompileManager.compileReport(getReportTemplate(type));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
        return jasperPrint;
    }


    public JRPdfExporter exportPDF(Collection<?> beanCollection,HttpServletResponse response, String type, HashMap<String, Object> parameters) throws JRException, IOException {
        JRPdfExporter exporter = new JRPdfExporter();
        JasperPrint jasperPrint = getJasperPrint(beanCollection, type, parameters);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        response.setContentType("application/pdf");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="  + " .pdf;");
        return exporter;
    }

//    public HtmlExporter exportHTML(){
//        HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
//        JasperPrint jasperPrint = getJasperPrint(beanCollection, type, parameters);
//        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
//        response.setContentType("text/html");
//        return exporter;
//    }

    public File getFilePDF(Collection<?> beanCollection, String type, String timeRange, String dateString) throws IOException, JRException, ParseException {
        JasperReport jasperReport = JasperCompileManager.compileReport(getReportTemplate(type));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanCollection);

        HashMap<String, Object> parameters = setParameters(type, timeRange, dateString);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

        File pdf = File.createTempFile(String.valueOf(parameters.get("title")), ".pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
        return pdf;
    }

}
