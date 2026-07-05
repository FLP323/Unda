package it.ur3.siw.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Playlist {

	/* ATTRIBUTI */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	private String descrizione;

	/* ASSOCIAZIONI */
	
	// Associazione con esattamente un utente
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"playlists", "password"})
	private Utente autore;

	// Associazione con una o più canzoni
	@ManyToMany
	@OrderColumn(name = "posizione") // mantiene l'ordine di inserimento
	@JsonIgnoreProperties({"playlists", "album"})
	private List<Canzone> canzoni;

	/* GETTERS E SETTERS */
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}

	public List<Canzone> getCanzoni() {
		return canzoni;
	}

	public void setCanzoni(List<Canzone> canzoni) {
		this.canzoni = canzoni;
	}

	/* EQUALS E HASHCODE */
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Playlist other = (Playlist) obj;
		return Objects.equals(id, other.id);
	}
	
}
