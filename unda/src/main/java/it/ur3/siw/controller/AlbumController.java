package it.ur3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.ur3.siw.model.Album;
import it.ur3.siw.model.enums.Genere;
import it.ur3.siw.service.AlbumService;
import it.ur3.siw.service.ArtistaService;

@Controller
public class AlbumController {

	private final AlbumService albumService;
	private final ArtistaService artistaService;

	AlbumController(AlbumService albumService, ArtistaService artistaService) {
		this.albumService = albumService;
		this.artistaService = artistaService;
	}

	// ------------------- Pubblico -------------------
	
	@GetMapping("/albums/{id}")
	public String albumDetail(@PathVariable Long id, Model model) {
		Album album = albumService.findByIdWithDetails(id);
		model.addAttribute("album", album);
		return "albums/show";
	}

	// ------------------- Admin -------------------
	
	@GetMapping("/admin/albums/new")
	public String newAlbumForm(Model model) {
		model.addAttribute("album", new Album());
		model.addAttribute("allArtists", artistaService.findAll());
		model.addAttribute("allGenres", Genere.values());
		return "admin/albums/form";
	}

	@PostMapping("/admin/albums")
	public String createAlbum(@ModelAttribute Album album) {
	    albumService.createAlbum(album);
	    return "redirect:http://127.0.0.1:5173/admin/albums/" + album.getId() + "/edit";
	}

	@GetMapping("/admin/albums/{id}/edit")
	public String editAlbumForm(@PathVariable Long id, Model model) {
		Album album = albumService.findByIdWithDetails(id);
		model.addAttribute("album", album);
		model.addAttribute("allArtists", artistaService.findAll());
		model.addAttribute("allGenres", Genere.values());
		return "admin/albums/edit";
	}

	@PostMapping("/admin/albums/{id}")
	public String updateAlbum(@PathVariable Long id, @ModelAttribute Album album) {
		albumService.updateAlbum(id, album);
		return "redirect:http://127.0.0.1:5173/admin/albums/" + id + "/edit";
	}

	@PostMapping("/admin/albums/{id}/delete")
	public String deleteAlbum(@PathVariable Long id) {
		albumService.deleteAlbum(id);
		return "redirect:/";
	}
}