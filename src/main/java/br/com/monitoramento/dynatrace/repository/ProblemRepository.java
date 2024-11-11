package br.com.monitoramento.dynatrace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.monitoramento.dynatrace.models.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
	Page<Problem> findByTitleContainsOrDisplayIdContainsOrProblemIdContainsOrSeverityLevelContainsOrImpactLevelContainsOrStatusContainsOrRootCauseEntityNameContains
	(String title, String displayId, String problemId, String severityLevel, String impactLevel, String status, String rootCause, Pageable paginacao);

	@Query( value = "SELECT periodo.* FROM (SELECT p.* FROM problems p WHERE p.start_time BETWEEN :inicio AND :fim) AS periodo LEFT JOIN entities e ON (periodo.root_cause_entity_id = e.id) " +
			" WHERE periodo.id LIKE %:filtro% OR periodo.display_id LIKE %:filtro% OR periodo.problem_id LIKE %:filtro% OR periodo.impact_level LIKE " +
			" %:filtro% OR periodo.severity_level LIKE %:filtro% OR periodo.title LIKE %:filtro% OR e.name LIKE %:filtro% OR e.entity_id LIKE %:filtro%",
			countQuery = "SELECT COUNT(periodo.id) FROM (SELECT p.* FROM problems p WHERE p.start_time BETWEEN :inicio AND :fim ) AS periodo LEFT JOIN entities e ON (periodo.root_cause_entity_id = e.id) " +
					" WHERE periodo.id LIKE %:filtro% OR periodo.display_id LIKE %:filtro% OR periodo.problem_id LIKE %:filtro% OR periodo.impact_level LIKE " +
					" %:filtro% OR periodo.severity_level LIKE %:filtro% OR periodo.title LIKE %:filtro% OR e.name LIKE %:filtro% OR e.entity_id LIKE %:filtro%",
			nativeQuery = true)
	Page<Problem> findByFiltroDate(@Param("filtro")String filtro, @Param("inicio")String inicio, @Param("fim")String fim,  Pageable paginacao);

	Problem findByProblemId(String problemId);
	
	@Query( value = "SELECT p.solver FROM problems p WHERE p.solver LIKE %:nome%", nativeQuery = true)
	List<String> findBySolver(@Param("nome")String nome);
	
	Page<Problem> findByValidated(Boolean validated, Pageable paginacao);

	@Query( value = "SELECT valid.id, valid.display_id, valid.business_impact, valid.end_time, valid.impact_level, valid.problem_id, " +
			" valid.severity_level, valid.start_time, valid.status, valid.title, valid.validated, valid.root_cause_entity_id, valid.solver " +
			" FROM (SELECT * FROM problems p WHERE p.validated = :validated) AS valid LEFT JOIN entities e ON (valid.root_cause_entity_id = e.id) " +
			" WHERE valid.id LIKE %:filtro% OR valid.display_id LIKE %:filtro% OR valid.problem_id LIKE %:filtro% OR valid.impact_level LIKE " +
			" %:filtro% OR valid.severity_level LIKE %:filtro% OR valid.title LIKE %:filtro% OR e.name LIKE %:filtro% OR e.entity_id LIKE %:filtro%",
			countQuery = "SELECT COUNT(valid.id) FROM (SELECT * FROM problems p WHERE p.validated = :validated) AS " +
			"valid LEFT JOIN entities e ON (valid.root_cause_entity_id = e.id) WHERE valid.id LIKE %:filtro% OR valid.display_id " +
					" LIKE %:filtro% OR valid.problem_id LIKE  %:filtro% OR valid.impact_level LIKE %:filtro% OR " +
			"valid.severity_level LIKE %:filtro% OR valid.title LIKE %:filtro% OR e.name LIKE %:filtro% OR e.entity_id LIKE %:filtro%",
			nativeQuery = true)
	Page<Problem> findByValidatedEFiltro(@Param("validated")Boolean validated,  @Param("filtro") String filtro, Pageable paginacao);

	@Query( value = "SELECT valid.id, valid.display_id, valid.business_impact, valid.end_time, valid.impact_level, valid.problem_id, " +
			" valid.severity_level, valid.start_time, valid.status, valid.title, valid.validated, valid.root_cause_entity_id, valid.solver " +
			" FROM (SELECT * FROM problems p WHERE p.validated = :validated AND p.start_time BETWEEN :inicio AND :fim) AS valid LEFT JOIN entities e ON (valid.root_cause_entity_id = e.id) " +
			" WHERE valid.id LIKE %:filtro% OR valid.display_id LIKE %:filtro% OR valid.problem_id LIKE %:filtro% OR valid.impact_level LIKE " +
			" %:filtro% OR valid.severity_level LIKE %:filtro% OR valid.title LIKE %:filtro% OR e.name LIKE %:filtro% OR e.entity_id LIKE %:filtro%",
			countQuery = "SELECT COUNT(valid.id) FROM (SELECT * FROM problems p WHERE p.validated = :validated AND p.start_time BETWEEN :inicio AND :fim) AS " +
					"valid LEFT JOIN entities e ON (valid.root_cause_entity_id = e.id) WHERE valid.id LIKE %:filtro% OR valid.display_id " +
					" LIKE %:filtro% OR valid.problem_id LIKE  %:filtro% OR valid.impact_level LIKE %:filtro% OR " +
					"valid.severity_level LIKE %:filtro% OR valid.title LIKE %:filtro% OR e.name LIKE %:filtro% OR e.entity_id LIKE %:filtro%",
			nativeQuery = true)
	Page<Problem> findByValidatedEFiltroIntervalo(@Param("validated")Boolean validated, @Param("filtro")String filtro, @Param("inicio")String inicio, @Param("fim")String fim, Pageable paginacao);

	@Query( value = "SELECT * FROM problems p WHERE p.start_time BETWEEN :inicio AND :fim",
			countQuery = "SELECT p.id FROM problems p WHERE p.start_time BETWEEN :inicio AND :fim",
			nativeQuery = true)
	Page<Problem> findByDate(@Param("inicio")String inicio, @Param("fim")String fim, Pageable paginacao);
}
