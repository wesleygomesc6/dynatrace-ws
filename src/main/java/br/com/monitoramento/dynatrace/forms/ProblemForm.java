package br.com.monitoramento.dynatrace.forms;

import java.util.Date;
import java.util.List;

import br.com.monitoramento.dynatrace.models.BusinessImpact;
import br.com.monitoramento.dynatrace.models.Entitie;
import br.com.monitoramento.dynatrace.models.ImpactLevelType;
import br.com.monitoramento.dynatrace.models.Problem;
import br.com.monitoramento.dynatrace.repository.ProblemRepository;

public class ProblemForm {
	
	private String problemId;
	private String displayId;
	private String title;
	private Date startTime;
	private Date endTime;
	private String severityLevel;
	private String impactLevel;
	private List<ImpactLevelType> impactLevels;
	private String status;
	private Boolean validated;
	private BusinessImpact businessImpact;
	private Entitie rootCauseEntity;
	private List<Entitie> affectedEntities;
	private List<Entitie> impactedEntities;
	private List<Entitie> evidences;
	private String solver;
	
	
	public void setAffectedEntities(List<Entitie> affectedEntities) {
		this.affectedEntities = affectedEntities;
	}
	
	public void setSolver(String solver) {
		this.solver = solver;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setSeverityLevel(String severityLevel) {
		this.severityLevel = severityLevel;
	}
	
	public void setImpactLevel(String impactLevel) {
		this.impactLevel = impactLevel;
	}
	public void setImpactLevels(List<ImpactLevelType> impactLevels) {
		this.impactLevels = impactLevels;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public void setRootCauseEntity(Entitie rootCauseEntity) {
		this.rootCauseEntity = rootCauseEntity;
	}
	public void setAffectedsEntities(List<Entitie> affectedEntities) {
		this.affectedEntities = affectedEntities;
	}
	public void setImpactedEntities(List<Entitie> impactedEntities) {
		this.impactedEntities = impactedEntities;
	}
	public void setEvidences(List<Entitie> evidences) {
		this.evidences = evidences;
	}
	
	public void setBusinessImpact(BusinessImpact businessImpact) {
		this.businessImpact = businessImpact;
	}
	public Problem converter() {
		// TODO Auto-generated method stub
		return new Problem(problemId, displayId, title, startTime, endTime, severityLevel, impactLevel, impactLevels, status,
				validated, businessImpact, rootCauseEntity, affectedEntities, impactedEntities, evidences, solver);
	}
	
	public Problem update(Long id, ProblemRepository problemRepository) {
		Problem problem = problemRepository.findByProblemId(problemId);
		problem.setProblemId(problemId);
		problem.setDisplayId(displayId);
		problem.setTitle(title);
		problem.setStartTime(startTime);
		problem.setEndTime(endTime);
		problem.setSeverityLevel(severityLevel);
		problem.setImpactLevel(impactLevel);
		problem.setImpactLevels(impactLevels);
		problem.setStatus(status);
		problem.setValidated(validated);
		problem.setBusinessImpact(businessImpact);
		problem.setRootCauseEntity(rootCauseEntity);
		problem.setAffectedEntities(affectedEntities);
		problem.setImpactedEntities(impactedEntities);
		problem.setEvidences(evidences);
		problem.setSolver(solver);
		
		return problem;
	}


}
