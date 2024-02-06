package com.maantt.otj.otjservice.controller;


import java.util.ArrayList;
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

import jakarta.annotation.PostConstruct;

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
	public List<AssessmentReport> list(){
		return service.listAllReport();
	}
	
    
	@GetMapping("/validation")
	public void validateExcelAndSave(){

	}
	
	@GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> generatePdfReport(@PathVariable int id) {
        try {
            AssessmentReport masterDetails = service.findReportById(id);
            // Generate PDF using PdfGeneratorService
            ResponseEntity<byte[]> response = pdfGeneratorService.generatePdfReport(masterDetails);
            return response;
        } 
        catch (Exception e) {
            LOGGER.error("Error generating PDF report", e);
            // Return an error response if something goes wrong
            return ResponseEntity.status(500).body(null);
        }
        finally {
			
		}
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting report: " + e.getMessage());
        }
    }
	

    
	

	@PostMapping("/excel-upload")
	
    public ResponseEntity<ExcelUploadResponse> handleFileUpload(@RequestParam("file") MultipartFile file) {

        try {
            
            System.out.println("inside handleFileUpload()");

           
            List<String> errors = new ArrayList<>();
    		
            errors.add("Missing value");
            errors.add("Incorrect value");
            errors.add("Incomplete value");
            errors.add("Invalid Employee ID cell format");
            errors.add("Invalid Email format");
    			
            
//            AssessmentReport assessmentReport = new AssessmentReport();
//    		ExcelUploadResponse errorResponse = new ExcelUploadResponse("errors", errors);
//    		ExcelUploadResponse successResponse = new ExcelUploadResponse("success", assessmentReport);
    		

    		ExcelUploadResponse response = service.validateExcelAndSave(file);
    		 return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
           
        	e.printStackTrace();
        	return null;
        }
    }
	
//	@PostConstruct
//	private void postConstruct() {
//	    LOGGER.info("PdfGeneratorService is {}", pdfGeneratorService);
//	}
	
	


}



