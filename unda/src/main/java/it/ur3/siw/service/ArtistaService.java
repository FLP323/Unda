package it.ur3.siw.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.ur3.siw.model.Album;
import it.ur3.siw.model.Artista;
import it.ur3.siw.model.Canzone;
import it.ur3.siw.model.Playlist;
import it.ur3.siw.repository.AlbumRepository;
import it.ur3.siw.repository.ArtistaRepository;
import it.ur3.siw.repository.CanzoneRepository;

@Service
public class ArtistaService {

	private final ArtistaRepository artistaRepository;
	private final AlbumRepository albumRepository;
	private final CanzoneRepository canzoneRepository;

	public ArtistaService(ArtistaRepository artistaRepository, AlbumRepository albumRepository,
			CanzoneRepository canzoneRepository) {
		this.artistaRepository = artistaRepository;
		this.albumRepository = albumRepository;
		this.canzoneRepository = canzoneRepository;
	}

	@Transactional(readOnly = true)
	public List<Artista> findAll() {
		return artistaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Artista findByIdWithAlbums(Long id) {
		return artistaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Artista non trovato con id: " + id));
	}

	@Transactional
	public Artista createArtista(Artista artista) {
		return artistaRepository.save(artista);
	}

	@Transactional
	public Artista updateArtista(Long id, Artista updated) {
		Artista existing = artistaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Artista non trovato"));
		existing.setName(updated.getName());
		existing.setBio(updated.getBio());
		existing.setImageUrl(updated.getImageUrl());
		if(updated.getGeneri() != null && !updated.getGeneri().isEmpty()) {
			existing.setGeneri(updated.getGeneri());
		}
		return artistaRepository.save(existing);
	}

	@Transactional
	public void deleteArtista(Long id) {
		Artista artista = artistaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Artista non trovato"));

		for (Album album : new HashSet<>(artista.getAlbums())) {
			for (Canzone canzone : album.getCanzoni()) {
				for (Playlist playlist : new HashSet<>(canzone.getPlaylists())) {
					playlist.getCanzoni().remove(canzone);
				}
				canzone.getPlaylists().clear();
			}
			albumRepository.delete(album);
		}
		artista.getAlbums().clear();

		// Elimina le canzoni dove l'artista è main artist
		for (Canzone canzone : new HashSet<>(artista.getMainSongs())) {
			for (Playlist playlist : new HashSet<>(canzone.getPlaylists())) {
				playlist.getCanzoni().remove(canzone);
			}
			canzone.getPlaylists().clear();
			canzoneRepository.delete(canzone);
		}

		// Elimina le canzoni dove l'artista è featuring
		for (Canzone canzone : new HashSet<>(artista.getFeaturingSongs())) {
			for (Playlist playlist : new HashSet<>(canzone.getPlaylists())) {
				playlist.getCanzoni().remove(canzone);
			}
			canzone.getPlaylists().clear();
			canzoneRepository.delete(canzone);
		}
		artistaRepository.delete(artista);
	}
}