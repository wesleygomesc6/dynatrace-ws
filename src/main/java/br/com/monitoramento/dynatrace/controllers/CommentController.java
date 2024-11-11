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

import br.com.monitoramento.dynatrace.dtos.CommentDto;
import br.com.monitoramento.dynatrace.forms.CommentForm;
import br.com.monitoramento.dynatrace.models.Comment;
import br.com.monitoramento.dynatrace.repository.CommentRepository;
import br.com.monitoramento.dynatrace.repository.ProblemRepository;
import io.swagger.annotations.Api;

@Api(tags = "Dynatrace / Comentarios")
@RestController
@RequestMapping("/dynatrace/comentarios")
@CrossOrigin
public class CommentController {
	
	@Autowired
	ProblemRepository problemRepository;
	
	
	@Autowired
	private CommentRepository commentRepository;
	

	@GetMapping
	@Cacheable(value = "listaComentarios")
	public Page<CommentDto> lista(@RequestParam(required = false) Long idProblema, @RequestParam(required = false) String filtro,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (idProblema != null) {
			Page<Comment> comment = commentRepository.findByProblemId(idProblema, paginacao);
			return CommentDto.converter(comment);
		} else if(filtro != null){
			Page<Comment> comment = commentRepository.findByAuthorNameContainsOrContentContains(filtro, filtro, paginacao);
			return CommentDto.converter(comment);
		} else { 
			Page<Comment> comment = commentRepository.findAll(paginacao);
			return CommentDto.converter(comment);
			
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CommentDto> detalhar(@PathVariable Long id) {
		Optional<Comment> comment = commentRepository.findById(id);
		if (comment.isPresent()) {
			return ResponseEntity.ok(new CommentDto(comment.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaComentarios", allEntries = true)
	public ResponseEntity<CommentDto> cadastrar(@RequestBody  CommentForm form, UriComponentsBuilder uriBuilder) {
		Comment comment = form.converter();
		commentRepository.save(comment);
		
		URI uri = uriBuilder.path("/commentarios/{id}").buildAndExpand(comment.getId()).toUri();
		return ResponseEntity.created(uri).body(new CommentDto(comment));
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaComentarios", allEntries = true)
	public ResponseEntity<CommentDto> atualizar(@PathVariable Long id, @RequestBody CommentForm form) {
		Optional<Comment> optional = commentRepository.findById(id);
		if (optional.isPresent()) {
			Comment comment = form.update(id, commentRepository);
			return ResponseEntity.ok(new CommentDto(comment));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaComentarios", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Comment> optional = commentRepository.findById(id);
		if (optional.isPresent()) {
			commentRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
