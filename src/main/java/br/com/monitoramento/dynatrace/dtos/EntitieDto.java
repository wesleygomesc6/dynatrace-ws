package br.com.monitoramento.dynatrace.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.dynatrace.models.Entitie;
import br.com.monitoramento.dynatrace.models.EntityType;

public class EntitieDto {
	private Long id;
	private String entityId;
	private String name;
	private EntityType entityType;
	
	public EntitieDto(Entitie entitie) {
		if(entitie != null) {
			this.id = entitie.getId();
			this.entityId = entitie.getEntityId();
			this.name = entitie.getName();
			this.entityType = entitie.getEntityType();
			
		}
	}

	public Long getId() {
		return id;
	}
	
	



	public String getEntityId() {
		return entityId;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public String getName() {
		return name;
	}

	

	public static Page<EntitieDto> converter(Page<Entitie> entities) {
		return entities.map(EntitieDto::new);
	}

}
