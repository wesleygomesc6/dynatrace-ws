package br.com.monitoramento.dynatrace.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.dynatrace.models.ImpactLevelType;

public class ImpactLevelTypeDto {
	private Long id;
	private String name;
	
	public ImpactLevelTypeDto(ImpactLevelType impactLevelType) {
		if(impactLevelType != null) {
			this.id = impactLevelType.getId();
			this.name = impactLevelType.getName();
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static Page<ImpactLevelTypeDto> converter(Page<ImpactLevelType> impactLevelTypeDto) {
		return impactLevelTypeDto.map(ImpactLevelTypeDto::new);
	}
	

}
