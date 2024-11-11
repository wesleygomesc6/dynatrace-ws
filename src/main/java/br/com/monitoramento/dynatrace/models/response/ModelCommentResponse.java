package br.com.monitoramento.dynatrace.models.response;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ModelCommentResponse {
	private Long totalCount;
	private Long pageSize;
	@OneToMany
	List<CommentResponse> comments = new ArrayList<>();
	
public ModelCommentResponse() {};
	
	public ModelCommentResponse(Long totalCount, Long pageSize, List<CommentResponse> comments) {
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.comments = comments;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public List<CommentResponse> getComments() {
		return comments;
	}

	public void setComments(List<CommentResponse> comments) {
		this.comments = comments;
	}
	
	

}
