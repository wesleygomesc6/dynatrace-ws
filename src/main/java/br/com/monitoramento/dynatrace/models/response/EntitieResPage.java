package br.com.monitoramento.dynatrace.models.response;

import java.util.List;

public class EntitieResPage {
	private Long totalCount;

	private Long pageSize;
	private List<EntitieResponseSelector> entities;
	
	public EntitieResPage() {}

	public List<EntitieResponseSelector> getEntities() {
		return entities;
	}

	public void setEntities(List<EntitieResponseSelector> entities) {
		this.entities = entities;
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
}
