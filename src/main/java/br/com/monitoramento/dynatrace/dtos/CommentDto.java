package br.com.monitoramento.dynatrace.dtos;

import org.springframework.data.domain.Page;

import br.com.monitoramento.dynatrace.models.Comment;

public class CommentDto {
	private Long id;
	private String authorName;
	private String content;
	private Long problemId;
	
	
	public CommentDto(Comment comment) {
		if(comment != null) {
			this.id = comment.getId();
			this.authorName = comment.getAuthorName();
			this.content = comment.getContent();
			this.problemId = comment.getProblem().getId();
		}
	}
	
	
	
	public Long getId() {
		return id;
	}


	public Long getProblemId() {
		return problemId;
	}



	public String getAuthorName() {
		return authorName;
	}
	public String getContent() {
		return content;
	}
	
	public static Page<CommentDto> converter(Page<Comment> comments) {
		return comments.map(CommentDto::new);
	}
	

}
