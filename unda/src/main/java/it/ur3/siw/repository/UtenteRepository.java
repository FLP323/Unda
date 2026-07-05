package it.ur3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ur3.siw.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	Optional<Utente> findByUsername(String username);

}
