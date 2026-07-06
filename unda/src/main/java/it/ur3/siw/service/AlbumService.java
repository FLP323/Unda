package it.ur3.siw.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
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
public class AlbumService {

	private final AlbumRepository albumRepository;
	private final CanzoneRepository canzoneRepository;
	private final ArtistaRepository artistaRepository;

	public AlbumService(AlbumRepository albumRepository, CanzoneRepository canzoneRepository,
			ArtistaRepository artistaRepository) {
		this.albumRepository = albumRepository;
		this.canzoneRepository = canzoneRepository;
		this.artistaRepository = artistaRepository;
	}

	@Transactional(readOnly = true)
	public Album findByIdWithDetails(Long id) {
		Album album = albumRepository.findWithArtistiById(id)
				.orElseThrow(() -> new NoSuchElementException("Album non trovato con id: " + id));

		List<Canzone> canzoniCompleta = canzoneRepository.findAllWithArtistiByAlbumId(album.getId());
		album.setCanzoni(canzoniCompleta);

		return album;
	}

	@Transactional
	public Album createAlbum(Album album) {
		if (album.getArtisti() != null && !album.getArtisti().isEmpty()) {
			Set<Artista> artistiReali = new HashSet<>();
			for (Artista artista : album.getArtisti()) {
				artistaRepository.findById(artista.getId()).ifPresent(artistiReali::add);
			}
			album.setArtisti(artistiReali);
		}

		if (album.getCanzoni() != null) {
			for (Canzone canzone : album.getCanzoni()) {
				canzone.setAlbum(album);
				if (canzone.getMainArtists() != null) {
					Set<Artista> mainReali = new HashSet<>();
					for (Artista a : canzone.getMainArtists()) {
						artistaRepository.findById(a.getId()).ifPresent(mainReali::add);
					}
					canzone.setMainArtists(mainReali);
				}
				if (canzone.getFeaturingArtists() != null) {
					Set<Artista> featReali = new HashSet<>();
					for (Artista a : canzone.getFeaturingArtists()) {
						artistaRepository.findById(a.getId()).ifPresent(featReali::add);
					}
					canzone.setFeaturingArtists(featReali);
				}
			}
		}

		Album savedAlbum = albumRepository.save(album);

		for (Artista artista : savedAlbum.getArtisti()) {
			artista.getAlbums().add(savedAlbum);
			artistaRepository.save(artista);
		}

		return savedAlbum;
	}

	@Transactional
	public Album updateAlbum(Long id, Album updated) {
		Album existing = albumRepository.findWithArtistiAndCanzoniById(id)
				.orElseThrow(() -> new NoSuchElementException("Album non trovato"));

		existing.setTitolo(updated.getTitolo());
		existing.setReleaseDate(updated.getReleaseDate());
		
		if(updated.getCoverImageUrl() != null) {
			existing.setCoverImageUrl(updated.getCoverImageUrl());
		}

	    if (updated.getGeneri() != null && !updated.getGeneri().isEmpty()) {
	        existing.setGeneri(updated.getGeneri());
	    }

		if (updated.getArtisti() != null) {
			Set<Artista> artistiReali = new HashSet<>();
			for (Artista a : updated.getArtisti()) {
				artistaRepository.findById(a.getId()).ifPresent(artistiReali::add);
			}
			existing.setArtisti(artistiReali);
		}

		if (updated.getCanzoni() != null && !updated.getCanzoni().isEmpty()) {
			for (Canzone oldSong : existing.getCanzoni()) {
				for (Playlist pl : oldSong.getPlaylists()) {
					pl.getCanzoni().remove(oldSong);
				}
				oldSong.getPlaylists().clear();
			}
			existing.getCanzoni().clear();

			for (Canzone newSong : updated.getCanzoni()) {
				newSong.setAlbum(existing);
				if (newSong.getMainArtists() != null) {
					Set<Artista> mainReali = new HashSet<>();
					for (Artista a : newSong.getMainArtists()) {
						artistaRepository.findById(a.getId()).ifPresent(mainReali::add);
					}
					newSong.setMainArtists(mainReali);
				}
				if (newSong.getFeaturingArtists() != null) {
					Set<Artista> featReali = new HashSet<>();
					for (Artista a : newSong.getFeaturingArtists()) {
						artistaRepository.findById(a.getId()).ifPresent(featReali::add);
					}
					newSong.setFeaturingArtists(featReali);
				}
				existing.getCanzoni().add(newSong);
			}
		}

		return albumRepository.save(existing);
	}

	@Transactional
	public void deleteAlbum(Long id) {
		Album album = albumRepository.findWithArtistiById(id)
				.orElseThrow(() -> new NoSuchElementException("Album non trovato"));
		for (Canzone canzone : album.getCanzoni()) {
			for (Playlist playlist : canzone.getPlaylists()) {
				playlist.getCanzoni().remove(canzone);
			}
			canzone.getPlaylists().clear();
		}
		albumRepository.delete(album);
	}
}