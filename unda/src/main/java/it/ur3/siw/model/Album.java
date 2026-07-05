package it.ur3.siw.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.ur3.siw.model.enums.Genere;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Album {
	
	/* ATTRIBUTI */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String titolo;

	@NotNull
	@Column(nullable = false)
	private LocalDate releaseDate;

	private String coverImageUrl;

	@ElementCollection
	@CollectionTable(name = "album_generi", joinColumns = @JoinColumn(name = "album_id"))
	@Column(name = "genere")
	@Enumerated(EnumType.STRING)
	private Set<Genere> generi;

	/* ASSOCIAZIONI */
	
	// Associazione con uno o più artisti
	@ManyToMany
	private Set<Artista> artisti;

	// Associazione con una o più canzoni
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("numeroTraccia ASC") // ordinamento per numero di tracccia crescente
	private List<Canzone> canzoni;

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

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public Set<Genere> getGeneri() {
		return generi;
	}

	public void setGeneri(Set<Genere> generi) {
		this.generi = generi;
	}

	public Set<Artista> getArtisti() {
		return artisti;
	}

	public void setArtisti(Set<Artista> artisti) {
		this.artisti = artisti;
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
		Album other = (Album) obj;
		return Objects.equals(id, other.id);
	}

}
