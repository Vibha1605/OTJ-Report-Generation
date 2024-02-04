package com.maantt.otj.otjservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "skill_cluster")

public class SkillCluster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "assessment_report_id")
	private AssessmentReport assessmentReport;
	
	@Column(name = "features")
	private String features;
	
	@Column(name = "topicwise_score")
	private int topicwiseScore;

	
	
	public SkillCluster() {
	}

	public SkillCluster(int id, AssessmentReport assessmentReport, String features, int topicwiseScore) {
		super();
		this.id = id;
		this.assessmentReport = assessmentReport;
		this.features = features;
		this.topicwiseScore = topicwiseScore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AssessmentReport getAssessmentReport() {
		return assessmentReport;
	}

	public void setAssessmentReport(AssessmentReport assessmentReport) {
		this.assessmentReport = assessmentReport;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public int getTopicwiseScore() {
		return topicwiseScore;
	}

	public void setTopicwiseScore(int topicwiseScore) {
		this.topicwiseScore = topicwiseScore;
	}

	@Override
	public String toString() {
		return String.format("SkillCluster [id=%s, features=%s, topicwiseScore=%s]", id,
				features, topicwiseScore);
	}

//	public void setScore(int score) {
//		// TODO Auto-generated method stub
//		this.score = score;
//	}
	
}
