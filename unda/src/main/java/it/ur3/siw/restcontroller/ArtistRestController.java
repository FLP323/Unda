package it.ur3.siw.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ur3.siw.model.Artista;
import it.ur3.siw.service.ArtistaService;

@RestController
@RequestMapping("/rest/artists")
public class ArtistRestController {

    private final ArtistaService artistaService;

    public ArtistRestController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public List<Artista> listArtists() {
        return artistaService.findAll();
    }
}