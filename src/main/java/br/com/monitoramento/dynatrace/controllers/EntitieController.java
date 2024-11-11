package br.com.monitoramento.dynatrace.controllers;

import br.com.monitoramento.dynatrace.dtos.EntitieDto;
import br.com.monitoramento.dynatrace.forms.EntitieForm;
import br.com.monitoramento.dynatrace.models.Entitie;
import br.com.monitoramento.dynatrace.models.EntityType;
import br.com.monitoramento.dynatrace.repository.EntitieRepository;
import br.com.monitoramento.dynatrace.repository.EntityTypeRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

@Api(tags = "Dynatrace / Entities")
@RestController
@RequestMapping("/dynatrace/entities")
@CrossOrigin
public class EntitieController {
	
	@Autowired
	private EntitieRepository entitieRepository;

	@Autowired
	private EntityTypeRepository entityTypeRepository;

	@GetMapping
	@Cacheable(value = "listaEntities")
	public Page<EntitieDto> lista(@RequestParam(required = false) String filtro, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (filtro == null) {
			Page<Entitie> entitie = entitieRepository.findAll(paginacao);
			return EntitieDto.converter(entitie);
		} else {
			Page<Entitie> entitie = entitieRepository.findByEntityIdContainsOrNameContainsOrEntityTypeNameContains(filtro, filtro,filtro, paginacao);
			return EntitieDto.converter(entitie);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntitieDto> detalhar(@PathVariable Long id) {
		Optional<Entitie> entitie = entitieRepository.findById(id);
		if (entitie.isPresent()) {
			return ResponseEntity.ok(new EntitieDto(entitie.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaEntities", allEntries = true)
	public ResponseEntity<EntitieDto> cadastrar(@RequestBody  EntitieForm form, UriComponentsBuilder uriBuilder) {
		Entitie entitie = form.converter();

		if(entitie.getEntityType() != null) {
			EntityType tipo = entityTypeRepository.findByName(entitie.getEntityType().getName());
			entitie.setEntityType(tipo);
		}

		entitieRepository.save(entitie);
		URI uri = uriBuilder.path("/entitie/{id}").buildAndExpand(entitie.getId()).toUri();
		return ResponseEntity.created(uri).body(new EntitieDto(entitie));
	}

	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaEntities", allEntries = true)
	public ResponseEntity<EntitieDto> atualizar(@PathVariable Long id, @RequestBody EntitieForm form) {
		Optional<Entitie> optional = entitieRepository.findById(id);
		if (optional.isPresent()) {
			Entitie entitie = form.update(id, entitieRepository);
			return ResponseEntity.ok(new EntitieDto(entitie));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaEntities", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Entitie> optional = entitieRepository.findById(id);
		if (optional.isPresent()) {
			entitieRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
