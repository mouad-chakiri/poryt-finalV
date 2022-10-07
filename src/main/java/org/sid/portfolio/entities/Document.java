package org.sid.portfolio.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@DiscriminatorValue("DOC")
public class Document extends Contenu {
	private String nomDoc;
	private String typeDoc;
	private String titre;
	@Lob
	private byte[] data;
	
	public Document() {
		super();
	}
		
	public Document(String nomDoc, String typeDoc, byte[] data,Chapitre chapitre) {
		super(chapitre);
		this.nomDoc = nomDoc;
		this.typeDoc = typeDoc;
		this.data = data;
	}
	
	public Document(String nomDoc, String typeDoc, byte[] data) {
		super();
		this.nomDoc = nomDoc;
		this.typeDoc = typeDoc;
		this.data = data;
	}
	public String getNomDoc() {
		return nomDoc;
	}
	public void setNomDoc(String nomDoc) {
		this.nomDoc = nomDoc;
	}
	public String getTypeDoc() {
		return typeDoc;
	}
	public void setTypeDoc(String typeDoc) {
		this.typeDoc = typeDoc;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	
}
