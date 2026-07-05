package it.ur3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.ur3.siw.model.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

	@Query("SELECT DISTINCT p FROM Playlist p " + 
			"LEFT JOIN FETCH p.autore " + 
			"LEFT JOIN FETCH p.canzoni c " +
			"LEFT JOIN FETCH c.mainArtists " +
			"LEFT JOIN FETCH c.featuringArtists " +
			"WHERE p.id = :id")
	Optional<Playlist> findWithAutoreAndCanzoniById(@Param("id") Long id);
	
	List<Playlist> findByAutoreId(Long id);
}