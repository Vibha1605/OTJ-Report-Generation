package com.maantt.otj.otjservice.service;


import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.repository.AssessmentReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
 
public class AssessmentReportService {
	@Autowired
	private AssessmentReportRepository repository;

	public List<AssessmentReport> listAllReport(){
		return repository.findAll();
	}

	public void save(AssessmentReport assessmentreport) {
		repository.save(assessmentreport);
	}
	
}