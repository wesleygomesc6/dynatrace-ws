package br.com.monitoramento.dynatrace.forms;

import br.com.monitoramento.dynatrace.models.EntityType;
import br.com.monitoramento.dynatrace.repository.EntityTypeRepository;

public class EntityTypeForm {
		private String name;

		public void setName(String name) {
			this.name = name;
		}
		
		public EntityType converter() {
			return new EntityType(name);
		}
		
		public EntityType update(Long id, EntityTypeRepository entityTypeRepository) {
			EntityType type = entityTypeRepository.getById(id);
			type.setName(name);
			return type;
		}
}
