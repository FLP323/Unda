package it.ur3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.ur3.siw.model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

	@EntityGraph(attributePaths = { "albums" })
	Optional<Artista> findById(Long id);
}