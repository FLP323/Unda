package it.ur3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.ur3.siw.model.Artista;
import it.ur3.siw.model.enums.Genere;
import it.ur3.siw.service.ArtistaService;

@Controller
public class ArtistController {

	private final ArtistaService artistaService;

	ArtistController(ArtistaService artistaService) {
		this.artistaService = artistaService;
	}

	// ------------------- Pubblico -------------------
	
	@GetMapping("/artists")
	public String listArtists(Model model) {
		model.addAttribute("artists", artistaService.findAll());
		return "artists/list";
	}

	@GetMapping("/artists/{id}")
	public String artistDetail(@PathVariable Long id, Model model) {
		Artista artista = artistaService.findByIdWithAlbums(id);
		model.addAttribute("artista", artista);
		return "artists/show";
	}

	// ------------------- Admin -------------------
	
	@GetMapping("/admin/artists/new")
	public String newArtistForm(Model model) {
		model.addAttribute("artista", new Artista());
		model.addAttribute("allGenres", Genere.values());
		return "admin/artists/form";
	}

	@PostMapping("/admin/artists")
	public String createArtist(@ModelAttribute Artista artista) {
		artistaService.createArtista(artista);
		return "redirect:/artists";
	}

	@GetMapping("/admin/artists/{id}/edit")
	public String editArtistForm(@PathVariable Long id, Model model) {
		Artista artista = artistaService.findByIdWithAlbums(id);
		model.addAttribute("artista", artista);
		model.addAttribute("allGenres", Genere.values());
		return "admin/artists/edit";
	}

	@PostMapping("/admin/artists/{id}")
	public String updateArtist(@PathVariable Long id, @ModelAttribute Artista artista) {
		artistaService.updateArtista(id, artista);
		return "redirect:/artists/" + id;
	}

	@PostMapping("/admin/artists/{id}/delete")
	public String deleteArtist(@PathVariable Long id) {
		artistaService.deleteArtista(id);
		return "redirect:/artists";
	}
}