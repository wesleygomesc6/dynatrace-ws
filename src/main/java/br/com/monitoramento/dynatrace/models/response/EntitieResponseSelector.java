package br.com.monitoramento.dynatrace.models.response;

public class EntitieResponseSelector {
	
	private String entityId;
	private String type;
	private String displayName;

	
	public EntitieResponseSelector () {};
	
	public EntitieResponseSelector (String entityId, String type, String displayName) {
		this.entityId = entityId;
		this.type = type;
		this.displayName = displayName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}






}
