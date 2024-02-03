package com.maantt.otj.otjservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.service.AssessmentReportService;


@RestController
@RequestMapping("/report")
@CrossOrigin(origins="http://localhost:4200/")

public class AssessmentReportController {
	//private static final  Logger LOGGER  = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private AssessmentReportService service;
	
	@GetMapping("/report")
	public List<AssessmentReport> list(){
		return service.listAllReport();
	}
	
	@GetMapping("/validation")
	public void ExcelVerification() {
		service.ExcelVerification();
		}
	}
