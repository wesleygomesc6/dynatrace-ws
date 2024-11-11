package br.com.monitoramento.dynatrace.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity (name = "comments")
public class Comment {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String authorName;
	@Column(columnDefinition="TEXT")
	private String content;
	@ManyToOne
	private Problem problem;
	
	public Comment () {}
	
	public Comment (String authorName, String content, Problem problem) {
		this.authorName = authorName;
		this.content = content;
		this.problem = problem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	

	
	

}
