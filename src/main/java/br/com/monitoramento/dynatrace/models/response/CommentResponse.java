package br.com.monitoramento.dynatrace.models.response;

import javax.persistence.Lob;
import java.util.Date;

public class CommentResponse {
	private String id;
	private Date createdAtTimestamp;
	@Lob
	private String content;
	private String authorName;
	
	public CommentResponse() {}
	
	public CommentResponse(String id, Date createdAtTimestamp, String content, String authorName) {
		this.id = id;
		this.createdAtTimestamp = createdAtTimestamp;
		this.content = content;
		this.authorName = authorName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAtTimestamp() {
		return createdAtTimestamp;
	}

	public void setCreatedAtTimestamp(Date createdAtTimestamp) {
		this.createdAtTimestamp = createdAtTimestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	

}
