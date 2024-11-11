package br.com.monitoramento.dynatrace.controllers;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.monitoramento.dynatrace.dtos.ImpactLevelTypeDto;
import br.com.monitoramento.dynatrace.forms.ImpactLevelTypeForm;
import br.com.monitoramento.dynatrace.models.ImpactLevelType;
import br.com.monitoramento.dynatrace.repository.ImpactLevelTypeRepository;
import io.swagger.annotations.Api;

@Api(tags = "Dynatrace / ImpactLevelsTypes")
@RestController
@RequestMapping("/dynatrace/impact-levels")
@CrossOrigin
public class ImpactLevelTypeController {
	
	
	
	@Autowired
	private ImpactLevelTypeRepository impactLevelTypeRepository;

	@GetMapping
	@Cacheable(value = "listaImpactLevelsTypes")
	public Page<ImpactLevelTypeDto> lista(@RequestParam(required = false) String name, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (name == null) {
			Page<ImpactLevelType> impactLevelType = impactLevelTypeRepository.findAll(paginacao);
			return ImpactLevelTypeDto.converter(impactLevelType);
		} else {
			Page<ImpactLevelType> impactLevelType = impactLevelTypeRepository.findByNameContains(name, paginacao);
			return ImpactLevelTypeDto.converter(impactLevelType);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ImpactLevelTypeDto> detalhar(@PathVariable Long id) {
		Optional<ImpactLevelType> impactLevelType = impactLevelTypeRepository.findById(id);
		if (impactLevelType.isPresent()) {
			return ResponseEntity.ok(new ImpactLevelTypeDto(impactLevelType.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaImpactLevelsTypes", allEntries = true)
	public ResponseEntity<ImpactLevelTypeDto> cadastrar(@RequestBody  ImpactLevelTypeForm form, UriComponentsBuilder uriBuilder) {
		ImpactLevelType impactLevelType = form.converter();
		impactLevelTypeRepository.save(impactLevelType);
		
		URI uri = uriBuilder.path("/impact-levels/{id}").buildAndExpand(impactLevelType.getId()).toUri();
		return ResponseEntity.created(uri).body(new ImpactLevelTypeDto(impactLevelType));
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaImpactLevelsTypes", allEntries = true)
	public ResponseEntity<ImpactLevelTypeDto> atualizar(@PathVariable Long id, @RequestBody ImpactLevelTypeForm form) {
		Optional<ImpactLevelType> optional = impactLevelTypeRepository.findById(id);
		if (optional.isPresent()) {
			ImpactLevelType impactLevelType = form.update(id, impactLevelTypeRepository);
			return ResponseEntity.ok(new ImpactLevelTypeDto(impactLevelType));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaImpactLevelsTypes", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<ImpactLevelType> optional = impactLevelTypeRepository.findById(id);
		if (optional.isPresent()) {
			impactLevelTypeRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
