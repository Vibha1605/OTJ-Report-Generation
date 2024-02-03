package com.maantt.otj.otjservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maantt.otj.otjservice.model.AssessmentReport;

@Repository
public interface AssessmentReportRepository extends JpaRepository<AssessmentReport, Long> {
    @SuppressWarnings("unchecked")
	// You can omit this method if you want to use the default save method
    AssessmentReport save(AssessmentReport assessmentReport);
}
