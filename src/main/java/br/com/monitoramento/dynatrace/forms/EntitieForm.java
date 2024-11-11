package br.com.monitoramento.dynatrace.forms;

import br.com.monitoramento.dynatrace.models.Entitie;
import br.com.monitoramento.dynatrace.models.EntityType;
import br.com.monitoramento.dynatrace.repository.EntitieRepository;

public class EntitieForm {
	private String entityId;
	private String name;
	private EntityType entityType;
	
	

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}


	public Entitie converter() {
		return new Entitie(entityId, name, entityType);
	}
	
	
	public Entitie update(Long id, EntitieRepository entitieRepository) {
		Entitie entitie = entitieRepository.getById(id);
		entitie.setEntityId(entityId);
		entitie.setName(name);
		entitie.setEntityType(entityType);
		return entitie;
	}
}
