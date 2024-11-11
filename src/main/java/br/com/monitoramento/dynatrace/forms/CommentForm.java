package br.com.monitoramento.dynatrace.forms;

import br.com.monitoramento.dynatrace.models.Comment;
import br.com.monitoramento.dynatrace.models.Problem;
import br.com.monitoramento.dynatrace.repository.CommentRepository;

public class CommentForm {
	private String authorName;
	private String content;
	private Problem problem;
	
	
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	public Comment converter() {
		return new Comment(authorName, content, problem);
	}
	
	public Comment update(Long id, CommentRepository commentRepository) {
		Comment comment = commentRepository.getOne(id);
		comment.setAuthorName(authorName);
		comment.setContent(content);
		comment.setProblem(problem);
		return comment;
	}
	
}
