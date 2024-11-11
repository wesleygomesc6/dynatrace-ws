package br.com.monitoramento.dynatrace.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.lang.NonNull;

@Entity(name = "entities")
public class Entitie {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String entityId;
	@Column(columnDefinition="TEXT")
	private String name;
	@ManyToOne @NonNull
	private EntityType entityType;

	
	public Entitie () {};
	
	public Entitie (String entityId, String name, EntityType entityType) {
		this.entityId = entityId;
		this.name = name;
		this.entityType = entityType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}





}
