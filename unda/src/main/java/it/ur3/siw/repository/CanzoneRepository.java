package it.ur3.siw.repository;

import it.ur3.siw.model.Canzone;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanzoneRepository extends JpaRepository<Canzone, Long> {

	@EntityGraph(attributePaths = { "mainArtists", "featuringArtists" })
	List<Canzone> findAllWithArtistiByAlbumId(Long id);

	List<Canzone> findByTitoloContainingIgnoreCase(String titolo);
}