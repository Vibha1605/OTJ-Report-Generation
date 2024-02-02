package com.maantt.otj.otjservice.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "assessment_report")
public class AssessmentReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "associate_id")
	private int associateId;
	
	@Column(name = "associate_name")
	private String associateName;
	
	@Column(name = "associate_email")
	private String associateEmail;
	
	@Column(name = "otj_name")
	private String otjName;
	
	@Column(name = "otj_code")
	private String otjCode;
	
	@Column(name = "otj_received_date")
	private Date otjReceivedDate;
	
	@Column(name = "planned_delivery_date")
	private Date plannedDeliveryDate;
	
	@Column(name = "actual_delivery_date")
	private Date actualDeliveryDate;
	
	@Column(name = "score")
	private int score;
	
	@Column(name = "status")
	private String status;
	
	@OneToMany(mappedBy = "assessmentReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SkillCluster> skillClusters;
	
	public AssessmentReport() {
	}

	public AssessmentReport(int id, int associateId, String associateName, String associateEmail, String otjName,
			String otjCode, Date otjReceivedDate, Date plannedDeliveryDate, Date actualDeliveryDate, int score,
			String status, List<SkillCluster> skillClusters) {
		super();
		this.id = id;
		this.associateId = associateId;
		this.associateName = associateName;
		this.associateEmail = associateEmail;
		this.otjName = otjName;
		this.otjCode = otjCode;
		this.otjReceivedDate = otjReceivedDate;
		this.plannedDeliveryDate = plannedDeliveryDate;
		this.actualDeliveryDate = actualDeliveryDate;
		this.score = score;
		this.status = status;
		this.skillClusters = skillClusters;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssociateId() {
		return associateId;
	}

	public void setAssociateId(int associateId) {
		this.associateId = associateId;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getAssociateEmail() {
		return associateEmail;
	}

	public void setAssociateEmail(String associateEmail) {
		this.associateEmail = associateEmail;
	}

	public String getOtjName() {
		return otjName;
	}

	public void setOtjName(String otjName) {
		this.otjName = otjName;
	}

	public String getOtjCode() {
		return otjCode;
	}

	public void setOtjCode(String otjCode) {
		this.otjCode = otjCode;
	}

	public Date getOtjReceivedDate() {
		return otjReceivedDate;
	}

	public void setOtjReceivedDate(Date otjReceivedDate) {
		this.otjReceivedDate = otjReceivedDate;
	}

	public Date getPlannedDeliveryDate() {
		return plannedDeliveryDate;
	}

	public void setPlannedDeliveryDate(Date plannedDeliveryDate) {
		this.plannedDeliveryDate = plannedDeliveryDate;
	}

	public Date getActualDeliveryDate() {
		return actualDeliveryDate;
	}

	public void setActualDeliveryDate(Date actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SkillCluster> getSkillClusters() {
		return skillClusters;
	}

	public void setSkillClusters(List<SkillCluster> skillClusters) {
		this.skillClusters = skillClusters;
	}

	@Override
	public String toString() {
		return String.format(
				"AssessmentReport [id=%s, associateId=%s, associateName=%s, associateEmail=%s, otjName=%s, otjCode=%s, otjReceivedDate=%s, plannedDeliveryDate=%s, actualDeliveryDate=%s, score=%s, status=%s]",
				id, associateId, associateName, associateEmail, otjName, otjCode, otjReceivedDate, plannedDeliveryDate,
				actualDeliveryDate, score, status);
	}
}
