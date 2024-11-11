package br.com.monitoramento.dynatrace.dtos;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.monitoramento.dynatrace.models.BusinessImpact;
import br.com.monitoramento.dynatrace.models.Entitie;
import br.com.monitoramento.dynatrace.models.ImpactLevelType;
import br.com.monitoramento.dynatrace.models.Problem;

public class ProblemDto {
	private Long id;
	private String problemId;
	private String displayId;
	private String title;
	private Date startTime;
	private Date endTime;
	private String duracao;
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
	
	public ProblemDto(Problem problem) {
		if(problem != null) {
			this.id = problem.getId();
			this.problemId = problem.getProblemId();
			this.displayId = problem.getDisplayId();
			this.title = problem.getTitle();
			this.startTime = problem.getStartTime();
			this.endTime = problem.getEndTime();
			this.duracao = problem.getDuracao();
			this.severityLevel = problem.getSeverityLevel();
			this.impactLevel = problem.getImpactLevel();
			this.impactLevels = problem.getImpactLevels();
			this.status = problem.getStatus();
			this.validated = problem.getValidated();
			this.businessImpact = problem.getBusinessImpact();
			this.rootCauseEntity = problem.getRootCauseEntity();
			this.affectedEntities = problem.getAffectedEntities();
			this.impactedEntities = problem.getImpactedEntities();
			this.evidences = problem.getEvidences();
			this.solver = problem.getSolver();
			
		}
	}
	

	public BusinessImpact getBusinessImpact() {
			return businessImpact;
	}


	public String getSolver() {
		return solver;
	}


	public Long getId() {
		return id;
	}

	public String getProblemId() {
		return problemId;
	}

	public String getDisplayId() {
		return displayId;
	}

	public String getTitle() {
		return title;
	}

	public String getSeverityLevel() {
		return severityLevel;
	}
	
	

	public String getImpactLevel() {
		return impactLevel;
	}

	public List<ImpactLevelType> getImpactLevels() {
		return impactLevels;
	}

	public String getStatus() {
		return status;
	}

	public Boolean getValidated() {
		return validated;
	}


	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getDuracao() {
		return duracao;
	}

	public Entitie getRootCauseEntity() {
		return rootCauseEntity;
	}

	public List<Entitie> getAffectedEntities() {
		return affectedEntities;
	}

	public List<Entitie> getImpactedEntities() {
		return impactedEntities;
	}

	public List<Entitie> getEvidences() {
		return evidences;
	}
	

	public static Page<ProblemDto> converter(Page<Problem> problems) {
		return problems.map(ProblemDto::new);
	}
	
	

}
