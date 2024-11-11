package br.com.monitoramento.dynatrace.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.dynatrace.models.EntityType;

public class EntityTypeDto {
	private Long id;
	private String name;
	
	public EntityTypeDto(EntityType entityType) {
		if(entityType != null) {
			this.id = entityType.getId();
			this.name = entityType.getName();
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static Page<EntityTypeDto> converter(Page<EntityType> entityType) {
		return entityType.map(EntityTypeDto::new);
	}
	

}
