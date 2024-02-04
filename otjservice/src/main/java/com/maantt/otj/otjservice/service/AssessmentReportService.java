package com.maantt.otj.otjservice.service;


import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.repository.AssessmentReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssessmentReportService {

    private final AssessmentReportRepository repository;

    @Autowired
    public AssessmentReportService(AssessmentReportRepository repository) {
        this.repository = repository;
    }

    public List<AssessmentReport> listAllReport() {
        return repository.findAll();
    }

    public AssessmentReport findReportById(int id) {
        return repository.findById(id);
    }

    public void save(AssessmentReport assessmentReport) {
        repository.save(assessmentReport);
    }
}
