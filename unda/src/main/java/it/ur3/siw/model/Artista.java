package it.ur3.siw.model;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.ur3.siw.model.enums.Genere;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Artista {

	/* ATTRIBUTI */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	private String bio;

	private String imageUrl;

	@ElementCollection
	@CollectionTable(name = "artista_generi", joinColumns = @JoinColumn(name = "artista_id"))
	@Column(name = "genere")
	@Enumerated(EnumType.STRING)
	private Set<Genere> generi;

	/* ASSOCIAZIONI */

	// Associazione con uno o più album
	@ManyToMany(mappedBy = "artisti")
	@JsonIgnoreProperties({"artisti", "canzoni"})
	private Set<Album> albums;
	
	// Associazioni con una o più canzoni (dove è main artist)
	@ManyToMany(mappedBy = "mainArtists")
	@JsonIgnore
	private Set<Canzone> mainSongs;

	// Associazioni con una o più canzoni (dove è featuring artist)
	@ManyToMany(mappedBy = "featuringArtists")
	@JsonIgnore
	private Set<Canzone> featuringSongs;

	/* GETTERS E SETTERS */
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Set<Genere> getGeneri() {
		return generi;
	}

	public void setGeneri(Set<Genere> generi) {
		this.generi = generi;
	}

	public Set<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}

	public Set<Canzone> getMainSongs() {
		return mainSongs;
	}

	public void setMainSongs(Set<Canzone> mainSongs) {
		this.mainSongs = mainSongs;
	}

	public Set<Canzone> getFeaturingSongs() {
		return featuringSongs;
	}

	public void setFeaturingSongs(Set<Canzone> featuringSongs) {
		this.featuringSongs = featuringSongs;
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
		Artista other = (Artista) obj;
		return Objects.equals(id, other.id);
	}
	
}
