package br.com.monitoramento.dynatrace.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.monitoramento.dynatrace.models.EntityType;

public interface EntityTypeRepository extends JpaRepository<EntityType, Long> {
	Page<EntityType> findByName(String name, Pageable paginacao);
	EntityType findByName(String type);

}
