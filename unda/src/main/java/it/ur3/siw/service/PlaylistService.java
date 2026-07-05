package it.ur3.siw.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.ur3.siw.model.Playlist;
import it.ur3.siw.model.Utente;
import it.ur3.siw.repository.PlaylistRepository;
import it.ur3.siw.repository.UtenteRepository;

@Service
public class PlaylistService {

	private final PlaylistRepository playlistRepository;
	private final UtenteRepository utenteRepository;

	public PlaylistService(PlaylistRepository playlistRepository, UtenteRepository utenteRepository) {
		this.playlistRepository = playlistRepository;
		this.utenteRepository = utenteRepository;
	}

	@Transactional(readOnly = true)
	public List<Playlist> findAll() {
		return playlistRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Playlist findByIdWithDetails(Long id) {
		return playlistRepository.findWithAutoreAndCanzoniById(id)
				.orElseThrow(() -> new NoSuchElementException("Playlist non trovata con id: " + id));
	}

	@Transactional(readOnly = true)
	public List<Playlist> findByUtente(Long utenteId) {
		return playlistRepository.findByAutoreId(utenteId);
	}

	@Transactional
	public Playlist createPlaylist(Playlist playlist, Long autoreId) {
		Playlist newPlaylist = new Playlist();
		newPlaylist.setNome(playlist.getNome());
		newPlaylist.setDescrizione(playlist.getDescrizione());
		Utente autore = utenteRepository.getReferenceById(autoreId);
		newPlaylist.setAutore(autore);
		if (playlist.getCanzoni() != null) {
			newPlaylist.setCanzoni(playlist.getCanzoni());
		}
		return playlistRepository.save(newPlaylist);
	}

	@Transactional
	public Playlist updatePlaylist(Long playlistId, Long utenteId, Playlist updated) {
		Playlist existing = playlistRepository.findWithAutoreAndCanzoniById(playlistId)
				.orElseThrow(() -> new NoSuchElementException("Playlist non trovata"));
		if (!existing.getAutore().getId().equals(utenteId)) {
			throw new SecurityException("Non sei il proprietario di questa playlist");
		}
		existing.setNome(updated.getNome());
		existing.setDescrizione(updated.getDescrizione());
		if (updated.getCanzoni() != null && !updated.getCanzoni().isEmpty()) {
			existing.setCanzoni(updated.getCanzoni());
		}
		return playlistRepository.save(existing);
	}

	@Transactional
	public void deletePlaylist(Long playlistId, Long utenteId) {
		Playlist playlist = playlistRepository.findById(playlistId)
				.orElseThrow(() -> new NoSuchElementException("Playlist non trovata"));
		if (!playlist.getAutore().getId().equals(utenteId)) {
			throw new SecurityException("Non sei il proprietario di questa playlist");
		}
		playlistRepository.delete(playlist);
	}
}