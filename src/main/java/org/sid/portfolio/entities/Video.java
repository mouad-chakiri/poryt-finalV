package org.sid.portfolio.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VID")
public class Video extends Contenu{
	private String url;
	
	public Video() {
		super();
	}
	
	public Video(String url,Chapitre chapitre) {
		super(chapitre);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
