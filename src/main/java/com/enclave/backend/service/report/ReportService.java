package com.enclave.backend.service.report;

import com.enclave.backend.entity.RevenueReport;
import com.enclave.backend.entity.report.EmployeeReport;
import com.enclave.backend.entity.report.ProductReport;
import com.enclave.backend.entity.report.ReportType;
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

    @Autowired
    private EmployeeReportService employeeReportService;

    @Autowired
    private ProductReportService productReportService;

    @Autowired
    private RevenueReportService revenueReportService;

    private List<EmployeeReport> addEmployeeToList(List<Object[]> queryResult){
        List<EmployeeReport> employeeReportList = new ArrayList<>();
        for (int i = 0; i < queryResult.size(); i++) {
            EmployeeReport employeeReport = new EmployeeReport();
            employeeReport.setId(i);
            employeeReport.setName((String) queryResult.get(i)[0]);
            Integer quantity = ((BigInteger) queryResult.get(i)[1]).intValue();
            employeeReport.setQuantity(quantity);
            employeeReport.setRevenue((Double) queryResult.get(i)[2]);
            employeeReportList.add(employeeReport);
        }
        return employeeReportList;
    }

    public List<EmployeeReport> getEmployeesForBranch(short branchId, String date, String timeRange) {
        List<Object[]> queryResult = employeeReportService.getEachBranch(branchId, date, timeRange);
        return addEmployeeToList(queryResult);
    }

    private List<ProductReport> addProductToList(List<Object[]> queryResult){
        List<ProductReport> productReportList = new ArrayList<>();
        for (int i = 0; i < queryResult.size(); i++) {
            ProductReport productReport = new ProductReport();
            productReport.setId(i);
            productReport.setName((String) queryResult.get(i)[0]);
            Integer quantity = ((BigDecimal) queryResult.get(i)[1]).intValue();
            productReport.setQuantity(quantity);
            productReport.setRevenue((Double) queryResult.get(i)[2]);
            productReportList.add(productReport);
        }
        return productReportList;
    }

    public List<ProductReport> getProductsByCategoryForBranch(short branchId, short categoryId, String date, String timeRange) {
        List<Object[]> queryResult = productReportService.getByTypeEachBranch(branchId, categoryId, date, timeRange);
        return addProductToList(queryResult);
    }

    public List<ProductReport> getProductsByCategory(short categoryId, String date, String timeRange) {
        List<Object[]> queryResult = productReportService.getProductByType(categoryId, date, timeRange);
        return addProductToList(queryResult);
    }

    private List<RevenueReport> addRevenueToList(List<Object[]> queryResult){
        List<RevenueReport> revenueReports = new ArrayList<>();
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

    public List<RevenueReport> getRevenueAllBranch(String date, String timeRange) {
        List<Object[]> queryResult = revenueReportService.getAllBranch(date, timeRange);
        return addRevenueToList(queryResult);
    }

    public void generateReportAllBranch(ExportType exportType, HttpServletResponse response, ReportType type, String date, String timeRange, short categoryId) throws JRException, IOException, ParseException, PrinterException {
        if (ReportType.Revenue.equals(type)) {
            List<RevenueReport> revenueReportList = getRevenueAllBranch(date, timeRange);
            exportReport(revenueReportList, exportType, response, type, timeRange, date);
        } else {
            List<ProductReport> productReportList = getProductsByCategory(categoryId, date, timeRange);
            exportReport(productReportList, exportType, response, type, timeRange, date);
        }
    }

    public void generateReportForBranch(ExportType exportType, HttpServletResponse response, ReportType type, short branchId, String date, String timeRange, short categoryId) throws JRException, IOException, ParseException, PrinterException {
        if (ReportType.Employee.equals(type)) {
            List <EmployeeReport> employeeReportList = getEmployeesForBranch(branchId, date, timeRange);
            exportReport(employeeReportList, exportType, response, type, timeRange, date);
        } else {
            List <ProductReport> productReportList = getProductsByCategoryForBranch(branchId, categoryId, date, timeRange);
            exportReport(productReportList, exportType, response, type, timeRange, date);
        }
    }

    private HashMap<String, Object> setParameters(ReportType type, String timeRange, String dateString) throws ParseException {
        HashMap<String, Object> parameters = new HashMap();
        String title = "";
        if (ReportType.Employee.equals(type)) {
            title = "Employee Report";
        } else if (ReportType.Revenue.equals(type)) {
            title = "Revenue Report";
        } else {
            title = "Product Report";
        }
        parameters.put("title", title);
        parameters.put("timeRange", timeRange);

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        LocalDate startDate ;
        LocalDate endDate ;
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

    public InputStream getReportTemplate(ReportType type) {
        InputStream reportStream;
        if (ReportType.Revenue.equals(type)) {
            reportStream = getClass().getResourceAsStream("/templates/revenueReport.jrxml");
        } else {
            reportStream = getClass().getResourceAsStream("/templates/report.jrxml");
        }
        return reportStream;
    }

    public void exportReport(Collection<?> data, ExportType exportType, HttpServletResponse response, ReportType type, String timeRange, String dateString) throws JRException, IOException, ParseException, PrinterException {
        HashMap<String, Object> parameters = setParameters(type, timeRange, dateString);
        JRAbstractExporter exporter = null;

        if (exportType == ExportType.PDF) {
            String fileName = (String) parameters.get("title");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".pdf;");
            exporter = exportPDF(data, response, type, parameters);

        } else if (exportType == ExportType.HTML) {
             exporter = exportHTML(data, response, type, parameters);
        }
        assert exporter != null;
        exporter.exportReport();
    }
    public JasperPrint getJasperPrint(Collection<?> data, ReportType type, HashMap<String, Object> parameters) throws JRException {
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(data);
        JasperReport jasperReport = JasperCompileManager.compileReport(getReportTemplate(type));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
        return jasperPrint;
    }

    public JRPdfExporter exportPDF(Collection<?> data,HttpServletResponse response, ReportType type, HashMap<String, Object> parameters) throws JRException, IOException {
        JRPdfExporter exporter = new JRPdfExporter();
        JasperPrint jasperPrint = getJasperPrint(data, type, parameters);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        response.setContentType("application/pdf");
        return exporter;
    }

    public HtmlExporter exportHTML(Collection<?> data,HttpServletResponse response, ReportType type, HashMap<String, Object> parameters) throws JRException, IOException {
        HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
        JasperPrint jasperPrint = getJasperPrint(data, type, parameters);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        response.setContentType("text/html");
        return exporter;
    }

    public File getFilePDF(Collection<?> data, ReportType type, String timeRange, String dateString) throws IOException, JRException, ParseException {
        HashMap<String, Object> parameters = setParameters(type, timeRange, dateString);
        JasperPrint jasperPrint = getJasperPrint(data, type, parameters);
        File pdf = File.createTempFile(String.valueOf(parameters.get("title")), ".pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
        return pdf;
    }
}
