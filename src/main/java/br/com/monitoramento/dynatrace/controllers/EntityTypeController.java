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

import br.com.monitoramento.dynatrace.dtos.EntityTypeDto;
import br.com.monitoramento.dynatrace.forms.EntityTypeForm;
import br.com.monitoramento.dynatrace.models.EntityType;
import br.com.monitoramento.dynatrace.repository.EntityTypeRepository;
import io.swagger.annotations.Api;

@Api(tags = "Dynatrace / EntityTypes")
@RestController
@RequestMapping("/dynatrace/entity-types")
@CrossOrigin
public class EntityTypeController {
	
	
	
	@Autowired
	private EntityTypeRepository entityTypeRepository;

	@GetMapping
	@Cacheable(value = "listaEntityTypes")
	public Page<EntityTypeDto> lista(@RequestParam(required = false) String name, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (name == null) {
			Page<EntityType> entityType = entityTypeRepository.findAll(paginacao);
			return EntityTypeDto.converter(entityType);
		} else {
			Page<EntityType> entityType = entityTypeRepository.findByName(name, paginacao);
			return EntityTypeDto.converter(entityType);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntityTypeDto> detalhar(@PathVariable Long id) {
		Optional<EntityType> entityType = entityTypeRepository.findById(id);
		if (entityType.isPresent()) {
			return ResponseEntity.ok(new EntityTypeDto(entityType.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaEntityTypes", allEntries = true)
	public ResponseEntity<EntityTypeDto> cadastrar(@RequestBody  EntityTypeForm form, UriComponentsBuilder uriBuilder) {
		EntityType entityType = form.converter();
		entityTypeRepository.save(entityType);
		
		URI uri = uriBuilder.path("/entity-types/{id}").buildAndExpand(entityType.getId()).toUri();
		return ResponseEntity.created(uri).body(new EntityTypeDto(entityType));
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaEntityTypes", allEntries = true)
	public ResponseEntity<EntityTypeDto> atualizar(@PathVariable Long id, @RequestBody EntityTypeForm form) {
		Optional<EntityType> optional = entityTypeRepository.findById(id);
		if (optional.isPresent()) {
			EntityType entityType = form.update(id, entityTypeRepository);
			return ResponseEntity.ok(new EntityTypeDto(entityType));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaEntityTypes", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<EntityType> optional = entityTypeRepository.findById(id);
		if (optional.isPresent()) {
			entityTypeRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
