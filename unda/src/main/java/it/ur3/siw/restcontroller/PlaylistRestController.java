package it.ur3.siw.restcontroller;

import it.ur3.siw.model.Canzone;
import it.ur3.siw.model.Playlist;
import it.ur3.siw.model.Utente;
import it.ur3.siw.repository.CanzoneRepository;
import it.ur3.siw.repository.UtenteRepository;
import it.ur3.siw.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class PlaylistRestController {

	private final PlaylistService playlistService;
	private final UtenteRepository utenteRepository;
	private final CanzoneRepository canzoneRepository;

	public PlaylistRestController(PlaylistService playlistService, UtenteRepository utenteRepository,
			CanzoneRepository canzoneRepository) {
		this.playlistService = playlistService;
		this.utenteRepository = utenteRepository;
		this.canzoneRepository = canzoneRepository;
	}

	private Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Utente utente = utenteRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalStateException("Utente non trovato"));
		return utente.getId();
	}

	@GetMapping("/playlists/{id}")
	public ResponseEntity<Playlist> getPlaylist(@PathVariable Long id) {
		return ResponseEntity.ok(playlistService.findByIdWithDetails(id));
	}

	@PutMapping("/user/playlists/{id}")
	public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlist) {
		Long userId = getCurrentUserId();
		return ResponseEntity.ok(playlistService.updatePlaylist(id, userId, playlist));
	}

	@PostMapping("/user/playlists/{playlistId}/songs/{songId}")
	public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
		Long userId = getCurrentUserId();
		Playlist playlist = playlistService.findByIdWithDetails(playlistId);
		if (!playlist.getAutore().getId().equals(userId)) {
			throw new SecurityException("Accesso negato");
		}
		Canzone canzone = canzoneRepository.findById(songId)
				.orElseThrow(() -> new IllegalArgumentException("Canzone non trovata"));
		playlist.getCanzoni().add(canzone);
		playlistService.updatePlaylist(playlistId, userId, playlist);
		return ResponseEntity.ok(playlist);
	}

	@DeleteMapping("/user/playlists/{playlistId}/songs/{songId}")
	public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
		Long userId = getCurrentUserId();
		Playlist playlist = playlistService.findByIdWithDetails(playlistId);
		if (!playlist.getAutore().getId().equals(userId)) {
			throw new SecurityException("Accesso negato");
		}
		playlist.getCanzoni().removeIf(c -> c.getId().equals(songId));
		playlistService.updatePlaylist(playlistId, userId, playlist);
		return ResponseEntity.noContent().build();
	}
}