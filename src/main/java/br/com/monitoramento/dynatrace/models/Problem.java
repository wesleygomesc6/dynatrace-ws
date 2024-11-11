package br.com.monitoramento.dynatrace.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity (name = "problems")
public class Problem {
	

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String problemId;
	private String displayId;
	private String title;
	
	private Date startTime;
	private Date endTime;
	
	private String severityLevel;
	
	private String impactLevel;
	
	@ManyToMany
	@JoinTable(
			name = "problems_impact_levels",
			joinColumns = @JoinColumn(name = "problems_id"),
			inverseJoinColumns = @JoinColumn(name = "impact_levels_id")
		)
		private List<ImpactLevelType> impactLevels = new ArrayList<>();
	
	private String status;
	private Boolean validated;
	private BusinessImpact businessImpact;
	@ManyToOne
	private Entitie rootCauseEntity;
	
	@ManyToMany
	@JoinTable(
			name = "affected_entities",
			joinColumns = @JoinColumn(name = "problem_id"),
			inverseJoinColumns = @JoinColumn(name = "entity_id")
		)
		private List<Entitie> affectedEntities  =  new ArrayList<>();
	
	
	@ManyToMany
	@JoinTable(
			name = "impacted_entities",
			joinColumns = @JoinColumn(name = "problem_id"),
			inverseJoinColumns = @JoinColumn(name = "entity_id")
		)
		private List<Entitie> impactedEntities =  new ArrayList<>();
	
	@ManyToMany
	@JoinTable(
			name = "evidence_entities",
			joinColumns = @JoinColumn(name = "problem_id"),
			inverseJoinColumns = @JoinColumn(name = "entity_id")
		)
		private List<Entitie> evidences =  new ArrayList<>();
	private String solver;
	
	
	public Problem () {}
	
	public Problem (String problemId, String displayId, String title, Date startTime, Date endTime,
			String severityLevel, String impactLevel, List<ImpactLevelType> impactLevels, String status, Boolean validated, 
			BusinessImpact businessImpact, Entitie rootCauseEntity, List<Entitie> affectedEntities, List<Entitie> impactedEntities, 
			List<Entitie> evidences, String solver) {
		this.problemId = problemId;
		this.displayId = displayId;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.severityLevel = severityLevel;
		this.impactLevel = impactLevel;
		this.impactLevels = impactLevels;
		this.status = status;
		this.validated = (validated != null) ? validated : false ;
		this.businessImpact = (businessImpact != null) ? businessImpact : BusinessImpact.NAO;
		this.rootCauseEntity = rootCauseEntity;
		this.affectedEntities = affectedEntities;
		this.impactedEntities = impactedEntities;
		this.evidences = evidences;
		this.solver = solver;
	}
	
	public String getDuracao() {

			long segundos = (endTime.getTime() - 
					startTime.getTime()) / 1000;
			int dias = (int)Math.floor(segundos / 86400);
			segundos -= dias * 86400;
			int horas = (int)Math.floor(segundos / 3600);
			segundos -= horas * 3600;
			int minutos = (int)Math.floor(segundos / 60);
			segundos -= minutos * 60;
			
			if(dias < 1 && horas < 1 && minutos < 1) {
				String duracao = segundos + " segundos";
				return duracao;
			}else if(dias < 1 && horas < 1) {
				String duracao = minutos + " minutos";
				return duracao;
			} else if(dias < 1 && horas >= 1){
				String duracao = horas + " horas e " + minutos + " minutos";
				return duracao;
			} else {
				String duracao = dias + " dias, " + horas + " horas e " + minutos + " minutos";
				return duracao;
			}
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getSolver() {
		return solver;
	}

	public void setSolver(String solver) {
		this.solver = solver;
	}

	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(String severityLevel) {
		this.severityLevel = severityLevel;
	}
	

	public String getImpactLevel() {
		return impactLevel;
	}

	public void setImpactLevel(String impactLevel) {
		this.impactLevel = impactLevel;
	}

	public List<ImpactLevelType> getImpactLevels() {
		return impactLevels;
	}

	public void setImpactLevels(List<ImpactLevelType> impactLevels) {
		this.impactLevels = impactLevels;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated ;
	}
	
	
	

	public BusinessImpact getBusinessImpact() {
		return businessImpact;
	}

	public void setBusinessImpact(BusinessImpact businessImpact) {
		this.businessImpact = businessImpact;
	}

	public Entitie getRootCauseEntity() {
		return rootCauseEntity;
	}

	public void setRootCauseEntity(Entitie rootCauseEntity) {
		this.rootCauseEntity = rootCauseEntity;
	}

	public List<Entitie> getAffectedEntities() {
		return affectedEntities;
	}

	public void setAffectedEntities(List<Entitie> affectedEntities) {
		this.affectedEntities = affectedEntities;
	}

	public List<Entitie> getImpactedEntities() {
		return impactedEntities;
	}

	public void setImpactedEntities(List<Entitie> impactedEntities) {
		this.impactedEntities = impactedEntities;
	}

	public List<Entitie> getEvidences() {
		return evidences;
	}

	public void setEvidences(List<Entitie> evidences) {
		this.evidences = evidences;
	}
	
	
	

}
