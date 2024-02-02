package com.maantt.otj.otjservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maantt.otj.otjservice.model.AssessmentReport;

@Repository
public interface AssessmentReportRepository extends JpaRepository<AssessmentReport, Long> {
	//@Query("SELECT a FROM AssessmentReport a JOIN FETCH a.skillClusters")
	//List<AssessmentReport> getAllAssessmentReports();
}
