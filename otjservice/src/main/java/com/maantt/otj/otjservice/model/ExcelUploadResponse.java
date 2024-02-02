package com.maantt.otj.otjservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExcelUploadResponse {
	
	
	private String status;
	
	private List<String> errors;
	
	private AssessmentReport assessmentReport;


	public ExcelUploadResponse() {
		super();
	}


	public ExcelUploadResponse(String status, List<String> errors, AssessmentReport assessmentReport) {
		super();
		this.status = status;
		this.errors = errors;
		this.assessmentReport = assessmentReport;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<String> getErrors() {
		return errors;
	}


	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public AssessmentReport getReport() { 
		return assessmentReport;
	}


	public void setStatus(AssessmentReport assessmentReport) {
		this.assessmentReport = assessmentReport;
	}


	@Override
	public String toString() {
		return String.format("ExcelUploadResponse [status=%s, errors=%s, assessmentReport=%s]", status, errors,
				assessmentReport);
	}

}
