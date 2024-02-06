package com.maantt.otj.otjservice.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.maantt.otj.otjservice.model.AssessmentReport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class PdfGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(PdfGeneratorService.class);

    private static final String JRXML_PATH_PAGE_1 =  "../otjservice/src/main/resources/templates/Page_1.jrxml";
    private static final String JRXML_PATH_PAGE_2 = "../otjservice/src/main/resources/templates/Page_2.jrxml";
    private static final String JRXML_PATH_PAGE_3 = "../otjservice/src/main/resources/templates/Page_3.jrxml";

    public ResponseEntity<byte[]> generatePdfReport(AssessmentReport masterDetails) throws IOException, JRException {
        try {
            //log.info("{}", masterDetails);

            // Load JasperReport templates from JRXML
            JasperReport report1 = JasperCompileManager.compileReport(JRXML_PATH_PAGE_1);
            JasperReport report2 = JasperCompileManager.compileReport(JRXML_PATH_PAGE_2);
            JasperReport report3 = JasperCompileManager.compileReport(JRXML_PATH_PAGE_3);

            // Create custom datasource
            CustomDataSource dataSource1 = new CustomDataSource(masterDetails);
            CustomDataSource dataSource2 = new CustomDataSource(masterDetails);
            CustomDataSource dataSource3 = new CustomDataSource(masterDetails);

            // Create parameters
            Map<String, Object> parameters = createParameters(dataSource2);

            // Add the line to include JRBeanCollectionDataSource
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSource2.getFeatureScores());
            parameters.put("DataSource", beanCollectionDataSource);

            // Generate JasperPrint using the combined datasource
            JasperPrint jasperPrint1 = JasperFillManager.fillReport(report1, new HashMap<>(), dataSource1);
            JasperPrint jasperPrint2 = JasperFillManager.fillReport(report2, parameters, dataSource2);
            JasperPrint jasperPrint3 = JasperFillManager.fillReport(report3, new HashMap<>(), dataSource3);

            List<JasperPrint> jasperPrintList = createJasperPrintList(jasperPrint1, jasperPrint2, jasperPrint3);

            // Manually merge JasperPrint objects
            ByteArrayOutputStream mergedReportOutputStream = mergeJasperPrints(jasperPrintList);

            // Set response headers
            HttpHeaders headers = createPdfResponseHeaders(masterDetails.getAssociateName());

            // Log information
            log.info("Generated PDF report for Assessment Report ID: {}", masterDetails.getId());

            // Return the merged PDF bytes as a response
            return new ResponseEntity<>(mergedReportOutputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error generating PDF report", e);
            // Return an error response if something goes wrong
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private Map<String, Object> createParameters(CustomDataSource dataSource) {
        Map<String, Object> parameters = new HashMap<>();
        dataSource.populateOutputStrings();
        parameters.put("DEMONSTRATE_OUTPUT", dataSource.getDemonstrateOutput());
        //log.info("{}", dataSource.getDemonstrateOutput());
        parameters.put("IMPROVE_OUTPUT", dataSource.getImproveOutput());
        //log.info("{}", dataSource.getImproveOutput());
        parameters.put("FOCUS_OUTPUT", dataSource.getFocusOutput());
        //log.info("{}", dataSource.getFocusOutput());

        // Add more parameters as needed

        return parameters;
    }

    private List<JasperPrint> createJasperPrintList(JasperPrint... jasperPrints) {
        List<JasperPrint> jasperPrintList = new ArrayList<>();
        for (JasperPrint jasperPrint : jasperPrints) {
            jasperPrintList.add(jasperPrint);
        }
        return jasperPrintList;
    }

    private ByteArrayOutputStream mergeJasperPrints(List<JasperPrint> jasperPrintList) throws JRException {
        ByteArrayOutputStream mergedReportOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(mergedReportOutputStream));
        exporter.exportReport();
        return mergedReportOutputStream;
    }

    private HttpHeaders createPdfResponseHeaders(String associateName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", associateName + "-report.pdf");
        return headers;
    }
}
