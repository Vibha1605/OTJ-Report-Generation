package com.maantt.otj.otjservice.controller;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.service.AssessmentReportService;
import com.maantt.otj.otjservice.service.PdfGeneratorService;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "http://localhost:4200/")
public class AssessmentReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssessmentReportController.class);

    @Autowired
    private AssessmentReportService service;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping("/generate")
    public List<AssessmentReport> list() {
        return service.listAllReport();
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> generatePdfReport(@PathVariable int id) {
        try {
            AssessmentReport masterDetails = service.findReportById(id);
            // Generate PDF using PdfGeneratorService
            ResponseEntity<byte[]> response = pdfGeneratorService.generatePdfReport(masterDetails);
            return response;
        } catch (Exception e) {
            LOGGER.error("Error generating PDF report", e);
            // Return an error response if something goes wrong
            return ResponseEntity.status(500).body(null);
        }
    }
}