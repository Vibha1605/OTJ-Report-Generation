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
import com.maantt.otj.otjservice.model.SkillCluster;
import com.maantt.otj.otjservice.service.AssessmentReportService;


@RestController
@RequestMapping("/report")
@CrossOrigin

public class AssessmentReportController {
	//private static final  Logger LOGGER  = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private AssessmentReportService service;
	
	@GetMapping
	public List<AssessmentReport> list(){
		return service.listAllReport();
	}
	
	@PostMapping("/excel-upload")
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
            assessmentReport.setAssociateId(123456);
            assessmentReport.setAssociateName("Babu");
            assessmentReport.setAssociateEmail("babu@gmail.com");
            assessmentReport.setOtjName("Angular");
            assessmentReport.setOtjCode("24857209");
            assessmentReport.setOtjReceivedDate(new java.sql.Date(System.currentTimeMillis()));
            assessmentReport.setPlannedDeliveryDate(null);
            assessmentReport.setActualDeliveryDate(null);
            assessmentReport.setScore(98);
            assessmentReport.setStatus("Completed");
            SkillCluster skillCluster1 = new SkillCluster();
            skillCluster1.setId(1);
            skillCluster1.setFeatures("Feature1");
            skillCluster1.setTopicwiseScore(80);
            SkillCluster skillCluster2 = new SkillCluster();
            skillCluster2.setId(2);
            skillCluster2.setFeatures("Feature2");
            skillCluster2.setTopicwiseScore(50);
            
            List<SkillCluster> skillClusters = new ArrayList<>();
            skillClusters.add(skillCluster1);
            skillClusters.add(skillCluster2);
            assessmentReport.setSkillClusters(skillClusters);
            
    		ExcelUploadResponse errorResponse = new ExcelUploadResponse("errors", errors);
    		ExcelUploadResponse successResponse = new ExcelUploadResponse("success", assessmentReport);
    		
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
