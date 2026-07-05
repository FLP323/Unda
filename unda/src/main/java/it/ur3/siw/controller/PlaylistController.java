package it.ur3.siw.controller;

import it.ur3.siw.model.Playlist;
import it.ur3.siw.model.Utente;
import it.ur3.siw.repository.UtenteRepository;
import it.ur3.siw.service.PlaylistService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlaylistController {

	private final PlaylistService playlistService;
	private final UtenteRepository utenteRepository;

	PlaylistController(PlaylistService playlistService, UtenteRepository utenteRepository) {
		this.playlistService = playlistService;
		this.utenteRepository = utenteRepository;
	}

	private Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Utente utente = utenteRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalStateException("Utente non trovato"));
		return utente.getId();
	}

	// ------------------- Pubblico -------------------

	@GetMapping("/playlists")
	public String listPlaylists(Model model) {
		model.addAttribute("playlists", playlistService.findAll());
		return "playlists/list";
	}

	@GetMapping("/playlists/{id}")
	public String playlistDetail(@PathVariable Long id, Model model) {
		Playlist playlist = playlistService.findByIdWithDetails(id);
		model.addAttribute("playlist", playlist);
		return "playlists/show";
	}

	// ------------------- Utente -------------------
	
	@GetMapping("/user/playlists")
	public String myPlaylists(Model model) {
		Long userId = getCurrentUserId();
		model.addAttribute("playlists", playlistService.findByUtente(userId));
		return "user/playlists/list";
	}

	@GetMapping("/user/playlists/new")
	public String newPlaylistForm(Model model) {
		model.addAttribute("playlist", new Playlist());
		return "user/playlists/form";
	}

	@PostMapping("/user/playlists")
	public String createPlaylist(@ModelAttribute Playlist playlist) {
	    Long userId = getCurrentUserId();
	    Playlist saved = playlistService.createPlaylist(playlist, userId);
	    return "redirect:http://localhost:5173/user/playlists/" + saved.getId() + "/edit";
	}

	@GetMapping("/user/playlists/{id}/edit")
	public String editPlaylistForm(@PathVariable Long id, Model model) {
		Long userId = getCurrentUserId();
		Playlist playlist = playlistService.findByIdWithDetails(id);
		if (!playlist.getAutore().getId().equals(userId)) {
			throw new SecurityException("Accesso negato");
		}
		model.addAttribute("playlist", playlist);
		return "user/playlists/edit";
	}

	@PostMapping("/user/playlists/{id}")
	public String updatePlaylist(@PathVariable Long id, @ModelAttribute Playlist updated) {
		Long userId = getCurrentUserId();
		Playlist saved = playlistService.updatePlaylist(id, userId, updated);
		return "redirect:http://localhost:5173/user/playlists/" + saved.getId() + "/edit";
	}

	@PostMapping("/user/playlists/{id}/delete")
	public String deletePlaylist(@PathVariable Long id) {
		Long userId = getCurrentUserId();
		playlistService.deletePlaylist(id, userId);
		return "redirect:/user/playlists";
	}
}