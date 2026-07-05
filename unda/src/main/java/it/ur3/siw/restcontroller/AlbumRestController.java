package it.ur3.siw.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ur3.siw.model.Album;
import it.ur3.siw.service.AlbumService;

@RestController
@RequestMapping("/rest")
public class AlbumRestController {

    private final AlbumService albumService;

    public AlbumRestController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.findByIdWithDetails(id));
    }

    @PutMapping("/admin/albums/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album album) {
        return ResponseEntity.ok(albumService.updateAlbum(id, album));
    }
}