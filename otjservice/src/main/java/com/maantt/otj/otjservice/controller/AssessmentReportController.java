package com.maantt.otj.otjservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.model.ExcelUploadResponse;
import com.maantt.otj.otjservice.service.AssessmentReportService;


@RestController
@RequestMapping("/report")
@CrossOrigin

public class AssessmentReportController {
	
    @Autowired
    private AssessmentReportService service;
	
	@GetMapping
	public List<AssessmentReport> list(){
		return service.listAllReport();
	}
	

	@GetMapping("/validation")
	public void validateExcelAndSave(){

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
    			
            
            AssessmentReport assessmentReport = new AssessmentReport();
            
            
    		ExcelUploadResponse errorResponse = new ExcelUploadResponse("errors", errors);
    		ExcelUploadResponse successResponse = new ExcelUploadResponse("success", assessmentReport);
    		

    		ExcelUploadResponse response = service.validateExcelAndSave(file);
    		 return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
           
        	e.printStackTrace();
        	return null;
        }
    }


}

