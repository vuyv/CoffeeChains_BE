package com.enclave.backend.service.report;

import com.enclave.backend.entity.Report;
import com.enclave.backend.service.EmployeeReportService;
import com.enclave.backend.service.ProductReportService;
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
import java.awt.print.*;
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

    @Autowired
    private EmployeeReportService employeeReportService;

    @Autowired
    private ProductReportService productReportService;

    private List<Object[]> queryResult = new ArrayList<>();

    private List<Report> getProductsEachBranch(short branchId, short categoryId, String date, String timeRange) {
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

    private List<Report> getEmployeesEachBranch(short branchId, String date, String timeRange) {
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

    public void downloadReport(ExportType exportType, HttpServletResponse response, String type, short branchId, String date, String timeRange, short categoryId) throws JRException, IOException, ParseException, PrinterException {
        List<Report> list = new ArrayList<>();
        if (type.equals("Employee")) {
            list = getEmployeesEachBranch(branchId, date, timeRange);
        } else {
            list = getProductsEachBranch(branchId, categoryId, date, timeRange);
        }
        exportReport(list, exportType, response, type, timeRange, date);
        list.clear();
    }

    private HashMap<String, Object> setParameters(String type,String timeRange, String dateString) throws ParseException {
        HashMap<String, Object> parameters = new HashMap();
        String title = "";
        if (type.equals("Employee")) {
            title = "Employee Report";
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
        if(timeRange.equals("Daily")){
            startDate = LocalDate.from(startOfDay(date));
            endDate = LocalDate.from(endOfDay(date));
            parameters.put("startDate", startDate);
            parameters.put("endDate", endDate);
        }
        return parameters;
    }


    private void exportReport(Collection<?> beanCollection, ExportType exportType, HttpServletResponse response, String type, String timeRange, String dateString) throws JRException, IOException, ParseException, PrinterException {
//        InputStream reportStream = getClass().getResourceAsStream("/templates/report_" + exportType.toString().toLowerCase() + ".jrxml");

        InputStream reportStream = getClass().getResourceAsStream("/templates/report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(beanCollection);

        HashMap<String, Object> parameters = setParameters(type, timeRange, dateString);

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
//            JasperExportManager.exportReportToHtmlFile(jasperPrint, "C:\\Users\\S\\Downloads\\" + "report.html");

            HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
            response.setContentType("text/html");
//            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".html;");
            exporter.exportReport();

        }
    }
}
