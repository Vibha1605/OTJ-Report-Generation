package com.maantt.otj.otjcontroller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.model.ExcelUploadResponse;


@RestController
@RequestMapping("/api/excel")
@CrossOrigin(origins = "http://localhost:4200/")
public class AssessmentReportController {

    @PostMapping("/upload")
    public ResponseEntity<ExcelUploadResponse> handleFileUpload(@RequestParam("file") MultipartFile file) {

        try {
            // Add logic to process the uploaded Excel file
            // You can use Apache POI or another library to read the Excel file
            // Perform necessary backend operations with the data
            System.out.println("inside handleFileUpload()");

            // Check if the file is empty
            List<String> errors = new ArrayList<>();
    		
            errors.add("Missing value");
            errors.add("Incorrect value");
            errors.add("Incomplete value");
    			
            //ExcelUploadResponse assessmentReport = new ExcelUploadResponse("report", assessmentReport);
            AssessmentReport assessmentReport = new AssessmentReport();
            assessmentReport.setId(1);
            //Set the values
            
    		ExcelUploadResponse errorResponse = new ExcelUploadResponse("errors", errors);
    		ExcelUploadResponse successResponse = new ExcelUploadResponse("success", new ArrayList<String>());
    		
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            // Handle other exceptions or errors
//            response.put("status", "error");
//            response.put("message", "Error processing the file: " + e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        	e.printStackTrace();
        	return null;
        }
    }
}