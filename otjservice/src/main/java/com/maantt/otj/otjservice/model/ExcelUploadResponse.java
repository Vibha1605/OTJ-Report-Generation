package com.maantt.otj.otjservice.model;

import java.util.List;

public class ExcelUploadResponse {
	
	
	private String status;
	
	private List<String> errors;
	
	private AssessmentReport assessmentReport;


	public ExcelUploadResponse() {
		super();
	}


	public ExcelUploadResponse(String status, List<String> errors) {
		super();
		this.status = status;
		this.errors = errors;
	}
	
	public ExcelUploadResponse(String status, AssessmentReport assessmentReport) {
		this.status = status;
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


	public AssessmentReport getAssessmentReport() {
		return assessmentReport;
	}


	public void setAssessmentReport(AssessmentReport assessmentReport) {
		this.assessmentReport = assessmentReport;
	}


	@Override
	public String toString() {
		return String.format("ExcelUploadResponse [status=%s, errors=%s, assessmentReport=%s]", status, errors,
				assessmentReport);
	}
	
	

}
