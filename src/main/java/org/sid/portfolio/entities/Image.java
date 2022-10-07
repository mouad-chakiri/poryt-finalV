package org.sid.portfolio.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class Image {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String namePic;
	private String typePic;
	@Lob
	private byte[] contenu;
	
	@OneToOne(cascade = CascadeType.REMOVE,mappedBy = "image")
	private Cours cours;
	
	public Image(String namePic, String typePic, byte[] contenu) {
		super();
		this.namePic = namePic;
		this.typePic = typePic;
		this.contenu = contenu;
	}

	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNamePic() {
		return namePic;
	}

	public void setNamePic(String namePic) {
		this.namePic = namePic;
	}

	public String getTypePic() {
		return typePic;
	}

	public void setTypePic(String typePic) {
		this.typePic = typePic;
	}

	public byte[] getContenu() {
		return contenu;
	}

	public void setContenu(byte[] contenu) {
		this.contenu = contenu;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}
	
	
	
}
