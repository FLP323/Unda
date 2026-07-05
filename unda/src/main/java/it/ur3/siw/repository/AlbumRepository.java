package it.ur3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import it.ur3.siw.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

	@EntityGraph(attributePaths = { "artisti" })
	Optional<Album> findWithArtistiById(Long id);
	
	@EntityGraph(attributePaths = { "artisti", "canzoni" })
	Optional<Album> findWithArtistiAndCanzoniById(Long id);
}