package it.ur3.siw.model;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Canzone {

	/* ATTRIBUTI */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String titolo;

	@NotNull
	@Column(nullable = false)
	private Integer durata; // secondi

	@NotNull
	@Column(nullable = false)
	private Integer numeroTraccia;

	/* ASSOCIAZIONI */

	// Associazione con esattamente un album
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("canzoni")
	private Album album;

	// Associazione con uno o più artisti principali
	@ManyToMany
	@JoinTable(name = "canzone_main_artists", joinColumns = @JoinColumn(name = "canzone_id"), inverseJoinColumns = @JoinColumn(name = "artista_id"))
	@JsonIgnoreProperties({"mainSongs", "featuringSongs", "albums"})
	private Set<Artista> mainArtists;

	// Associazione con uno o più artisti featuring
	@ManyToMany
	@JoinTable(name = "canzone_featuring_artists", joinColumns = @JoinColumn(name = "canzone_id"), inverseJoinColumns = @JoinColumn(name = "artista_id"))
	@JsonIgnoreProperties({"mainSongs", "featuringSongs", "albums"})
	private Set<Artista> featuringArtists;

	// Associazione con una o più playlist
	@ManyToMany(mappedBy = "canzoni")
	@JsonIgnore
	private Set<Playlist> playlists;

	/* GETTERS E SETTERS */

	public Long getId() {
		return id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Integer getDurata() {
		return durata;
	}

	public void setDurata(Integer durata) {
		this.durata = durata;
	}
	
	public String getDurataFormattata() {
	    int minuti = durata / 60;
	    int secondi = durata % 60;
	    return String.format("%d:%02d", minuti, secondi);
	}

	public Integer getNumeroTraccia() {
		return numeroTraccia;
	}

	public void setNumeroTraccia(Integer numeroTraccia) {
		this.numeroTraccia = numeroTraccia;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Set<Artista> getMainArtists() {
		return mainArtists;
	}

	public void setMainArtists(Set<Artista> mainArtists) {
		this.mainArtists = mainArtists;
	}

	public Set<Artista> getFeaturingArtists() {
		return featuringArtists;
	}

	public void setFeaturingArtists(Set<Artista> featuringArtists) {
		this.featuringArtists = featuringArtists;
	}

	public Set<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Set<Playlist> playlists) {
		this.playlists = playlists;
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
		Canzone other = (Canzone) obj;
		return Objects.equals(id, other.id);
	}
}
