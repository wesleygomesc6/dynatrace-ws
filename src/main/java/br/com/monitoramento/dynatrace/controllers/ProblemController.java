package br.com.monitoramento.dynatrace.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.monitoramento.dynatrace.models.Comment;
import br.com.monitoramento.dynatrace.models.response.CommentResponse;
import br.com.monitoramento.dynatrace.models.response.ModelCommentResponse;
import br.com.monitoramento.dynatrace.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.monitoramento.dynatrace.dtos.ProblemDto;
import br.com.monitoramento.dynatrace.forms.ProblemForm;
import br.com.monitoramento.dynatrace.models.Problem;
import br.com.monitoramento.dynatrace.repository.EntitieRepository;
import br.com.monitoramento.dynatrace.repository.ProblemRepository;
import io.swagger.annotations.Api;

@Api(tags = "Dynatrace / problemas")
@RestController
@RequestMapping("/dynatrace/problemas")
@CrossOrigin
public class ProblemController {
	
	@Autowired
	ProblemRepository problemRepository;
	
	@Autowired
	EntitieRepository entitieRepository;

	@Autowired
	CommentRepository commentRepository;

	@Value("${spring.dynatrace.urlv2}")
	private String urlv2;

	@Value("${spring.dynatrace.token}")
	private String token;

	private static Logger logger = (Logger) LoggerFactory.getLogger(ProblemController.class);


	@GetMapping
	@Cacheable(value = "listaProblemas")
	public Page<ProblemDto> lista(@RequestParam(required = false) String filtro,@RequestParam(required = false) String inicio, @RequestParam(required = false) String fim,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {

		if (filtro == null && inicio == null && fim == null) {
			Page<Problem> problems = problemRepository.findAll(paginacao);
			return ProblemDto.converter(problems);
		} else if (inicio == null && fim == null){
			Page<Problem> problems = problemRepository.findByTitleContainsOrDisplayIdContainsOrProblemIdContainsOrSeverityLevelContainsOrImpactLevelContainsOrStatusContainsOrRootCauseEntityNameContains(filtro, 
					filtro, filtro, filtro, filtro, filtro, filtro, paginacao);
			return ProblemDto.converter(problems);
		} else if (filtro == null){
			Page<Problem> problems = problemRepository.findByDate(inicio, fim, paginacao);
			return ProblemDto.converter(problems);
		} else {
			Page<Problem> problems = problemRepository.findByFiltroDate(filtro, inicio, fim, paginacao);
			return ProblemDto.converter(problems);
		}
	}
	
	@GetMapping("/validated")
	@Cacheable(value = "listaProblemas")
	public Page<ProblemDto> listarValidated(@RequestParam(required = true) Boolean validated,@RequestParam(required = false) String filtro,
											@RequestParam(required = false) String inicio, @RequestParam(required = false) String fim,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		if(filtro == null && inicio == null && fim == null) {
			Page<Problem> problems = problemRepository.findByValidated(validated, paginacao);
			return ProblemDto.converter(problems);
		} else if (filtro != null && inicio == null && fim == null) {
			Page<Problem> problems = problemRepository.findByValidatedEFiltro(validated, filtro, paginacao);
			return ProblemDto.converter(problems);
		} else {
			Page<Problem> problems = problemRepository.findByValidatedEFiltroIntervalo(validated, filtro, inicio, fim, paginacao);
			return ProblemDto.converter(problems);
		}
	}
	
	@GetMapping("/solvers")
	@Cacheable(value = "listaSolvers")
	public List<String> listarSolvers(@RequestParam(required = true) String nome, 
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		List<String> solver = problemRepository.findBySolver(nome);
		return solver;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProblemDto> detalhar(@PathVariable Long id) {
		Optional<Problem> problem = problemRepository.findById(id);
		if (problem.isPresent()) {
			return ResponseEntity.ok(new ProblemDto(problem.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaProblemas", allEntries = true)
	public ResponseEntity<ProblemDto> cadastrar(@RequestBody ProblemForm form, UriComponentsBuilder uriBuilder) {
		Problem problem = form.converter();
		problemRepository.save(problem);
		
		URI uri = uriBuilder.path("/problemas/{id}").buildAndExpand(problem.getProblemId()).toUri();
		return ResponseEntity.created(uri).body(new ProblemDto(problem));
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaProblemas", allEntries = true)
	public ResponseEntity<ProblemDto> atualizar(@PathVariable Long id, @RequestBody ProblemForm form) {

		Problem problema = problemRepository.getReferenceById(id);
		if(problema != null) {
			String url = urlv2+"problems/"+problema.getProblemId()+"/comments?api-token="+token;

			ModelCommentResponse comments =  new RestTemplate().getForObject(url, ModelCommentResponse.class);

			if(comments.getComments().isEmpty()) {
				logger.info("Este problema nao possui comentarios.");
			} else {
				logger.info("Salvando comentario(s)...");
				for(CommentResponse comentario : comments.getComments()) {
					Comment comment = new Comment();
					comment.setAuthorName(comentario.getAuthorName());
					comment.setContent(comentario.getContent());
					comment.setProblem(problema);

					commentRepository.save(comment);
					logger.info("Comentario(s) salvo(s).");
				}

			}
		}



		Optional<Problem> optional = problemRepository.findById(id);
		if (optional.isPresent()) {
			Problem problem = form.update(id, problemRepository);
			return ResponseEntity.ok(new ProblemDto(problem));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaProblemas", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Problem> optional = problemRepository.findById(id);
		if (optional.isPresent()) {
			problemRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
