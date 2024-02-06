package com.maantt.otj.otjservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.model.ExcelUploadResponse;
import com.maantt.otj.otjservice.service.AssessmentReportService;
import com.maantt.otj.otjservice.service.PdfGeneratorService;
@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "http://localhost:4200")
public class AssessmentReportController {

    private static final Logger log = LoggerFactory.getLogger(AssessmentReportController.class);

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
            ResponseEntity<byte[]> response = pdfGeneratorService.generatePdfReport(masterDetails);
            return response;
        } catch (Exception e) {
            log.error("Error generating PDF report", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReport(@PathVariable int id) {
            service.deleteReportById(id);
    }
           

    @PostMapping("/excel-upload")
    public ResponseEntity<ExcelUploadResponse> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            ExcelUploadResponse response = service.validateExcelAndSave(file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error handling file upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
