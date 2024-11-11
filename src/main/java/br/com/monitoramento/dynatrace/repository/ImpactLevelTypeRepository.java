package br.com.monitoramento.dynatrace.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.dynatrace.models.ImpactLevelType;

public interface ImpactLevelTypeRepository extends JpaRepository<ImpactLevelType, Long> {
	
	Optional<ImpactLevelType> findById(Long id);
	
	Page<ImpactLevelType> findByNameContains(String name, Pageable paginacao);
	ImpactLevelType findByName(String name);

	Page<ImpactLevelType> findByName(String impactLevel, Pageable paginacao);

}
