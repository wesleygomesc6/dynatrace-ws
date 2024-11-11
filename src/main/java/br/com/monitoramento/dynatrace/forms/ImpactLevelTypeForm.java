package br.com.monitoramento.dynatrace.forms;

import br.com.monitoramento.dynatrace.models.ImpactLevelType;
import br.com.monitoramento.dynatrace.repository.ImpactLevelTypeRepository;

public class ImpactLevelTypeForm {
		private String name;

		public void setName(String name) {
			this.name = name;
		}
		
		public ImpactLevelType converter() {
			return new ImpactLevelType(name);
		}
		
		public ImpactLevelType update(Long id, ImpactLevelTypeRepository impactLevelTypeRepository) {
			ImpactLevelType type = impactLevelTypeRepository.getById(id);
			type.setName(name);
			return type;
		}
}
