-- ================================================
-- UTENTI
-- sequence utente_seq: nextval = 1, 51, 101, 151, 201
-- ================================================
insert into utente(id, username, password, role) values(nextval('utente_seq'), 'admin', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ROLE_ADMIN');       -- id=1
insert into utente(id, username, password, role) values(nextval('utente_seq'), 'user', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ROLE_USER');         -- id=51
insert into utente(id, username, password, role) values(nextval('utente_seq'), 'pinco', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ROLE_USER');        -- id=101
insert into utente(id, username, password, role) values(nextval('utente_seq'), 'paolo', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ROLE_ADMIN');       -- id=151
insert into utente(id, username, password, role) values(nextval('utente_seq'), 'utente1', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ROLE_USER');     -- id=201

-- ================================================
-- ARTISTI
-- sequence artista_seq: nextval = 1, 51, 101, 151
-- ================================================
insert into artista(id, name, bio, image_url) values(nextval('artista_seq'), 'Kendrick Lamar', 'Rapper, cantautore e produttore discografico statunitense.', 'https://shorturl.at/JYHCQ');  -- id=1
insert into artista(id, name, bio, image_url) values(nextval('artista_seq'), 'SZA', 'Cantautrice statunitense.', 'https://shorturl.at/5gLJB');                                                -- id=51
insert into artista(id, name, bio, image_url) values(nextval('artista_seq'), 'Drake', 'Rapper, cantante e attore canadese.', 'https://shorturl.at/1haNZ');                                    -- id=101
insert into artista(id, name, bio, image_url) values(nextval('artista_seq'), 'J. Cole', 'Rapper e produttore discografico statunitense.', 'https://shorturl.at/tNLZG');                      -- id=151

-- ================================================
-- GENERI ARTISTI
-- ================================================
insert into artista_generi (artista_id, genere) values (1, 'RAP');      -- Kendrick
insert into artista_generi (artista_id, genere) values (1, 'HIPHOP');   -- Kendrick
insert into artista_generi (artista_id, genere) values (51, 'RNB');     -- SZA
insert into artista_generi (artista_id, genere) values (101, 'RAP');    -- Drake
insert into artista_generi (artista_id, genere) values (101, 'POP');    -- Drake
insert into artista_generi (artista_id, genere) values (151, 'RAP');    -- J. Cole
insert into artista_generi (artista_id, genere) values (151, 'HIPHOP'); -- J. Cole

-- ================================================
-- ALBUM
-- sequence album_seq: nextval = 1, 51, 101, 151, 201
-- ================================================
insert into album(id, titolo, release_date, cover_image_url) values(nextval('album_seq'), 'Mr. Morale and the Big Steppers', '2022-05-13', 'https://shorturl.at/O5I5K');   -- id=1
insert into album(id, titolo, release_date, cover_image_url) values(nextval('album_seq'), 'SOS', '2022-12-09', 'https://rb.gy/0niw08');                                 -- id=51
insert into album(id, titolo, release_date, cover_image_url) values(nextval('album_seq'), 'For All the Dogs', '2023-10-06', 'https://tinyurl.com/yncc99ms');                   -- id=101
insert into album(id, titolo, release_date, cover_image_url) values(nextval('album_seq'), 'The Off-Season', '2021-05-14', 'https://tinyurl.com/32m29t3s');                -- id=151
insert into album(id, titolo, release_date, cover_image_url) values(nextval('album_seq'), 'Black Panther Soundtrack', '2018-02-09', 'https://tinyurl.com/55dx4u74');       -- id=201

-- ================================================
-- ALBUM <-> ARTISTI
-- ================================================
insert into album_artisti (albums_id, artisti_id) values (1, 1);      -- Mr. Morale - Kendrick
insert into album_artisti (albums_id, artisti_id) values (51, 51);    -- SOS - SZA
insert into album_artisti (albums_id, artisti_id) values (101, 101);  -- For All the Dogs - Drake
insert into album_artisti (albums_id, artisti_id) values (151, 151);  -- The Off-Season - J. Cole
insert into album_artisti (albums_id, artisti_id) values (201, 1);    -- Black Panther - Kendrick
insert into album_artisti (albums_id, artisti_id) values (201, 51);   -- Black Panther - SZA

-- ================================================
-- GENERI ALBUM
-- ================================================
insert into album_generi (album_id, genere) values (1, 'RAP');
insert into album_generi (album_id, genere) values (1, 'HIPHOP');
insert into album_generi (album_id, genere) values (51, 'RNB');
insert into album_generi (album_id, genere) values (101, 'RAP');
insert into album_generi (album_id, genere) values (101, 'POP');
insert into album_generi (album_id, genere) values (151, 'RAP');
insert into album_generi (album_id, genere) values (201, 'RAP');
insert into album_generi (album_id, genere) values (201, 'RNB');

-- ================================================
-- CANZONI
-- sequence canzone_seq: nextval = 1, 51, 101, 151, 201, 251, 301, 351, 401, 451, 501, 551
-- ================================================
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'N95', 198, 1, 1);                     -- id=1
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'Die Hard', 239, 2, 1);                -- id=51
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'Silent Hill', 220, 3, 1);              -- id=101
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'Kill Bill', 158, 1, 51);               -- id=151
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'Snooze', 201, 2, 51);                  -- id=201
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'Open Arms', 253, 3, 51);               -- id=251
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'Virginia Beach', 248, 1, 101);         -- id=301
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'First Person Shooter', 265, 2, 101);   -- id=351
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), '95 South', 210, 1, 151);               -- id=401
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'Amari', 209, 2, 151);                  -- id=451
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'All the Stars', 235, 1, 201);          -- id=501
insert into canzone(id, titolo, durata, numero_traccia, album_id) values(nextval('canzone_seq'), 'Pray for Me', 224, 2, 201);           -- id=551

-- ================================================
-- CANZONI <-> MAIN ARTISTS
-- ================================================
insert into canzone_main_artists (canzone_id, artista_id) values (1, 1);      -- N95 - Kendrick
insert into canzone_main_artists (canzone_id, artista_id) values (51, 1);     -- Die Hard - Kendrick
insert into canzone_main_artists (canzone_id, artista_id) values (101, 1);    -- Silent Hill - Kendrick
insert into canzone_main_artists (canzone_id, artista_id) values (151, 51);   -- Kill Bill - SZA
insert into canzone_main_artists (canzone_id, artista_id) values (201, 51);   -- Snooze - SZA
insert into canzone_main_artists (canzone_id, artista_id) values (251, 51);   -- Open Arms - SZA
insert into canzone_main_artists (canzone_id, artista_id) values (301, 101);  -- Virginia Beach - Drake
insert into canzone_main_artists (canzone_id, artista_id) values (351, 101);  -- First Person Shooter - Drake
insert into canzone_main_artists (canzone_id, artista_id) values (401, 151);  -- 95 South - J. Cole
insert into canzone_main_artists (canzone_id, artista_id) values (451, 151);  -- Amari - J. Cole
insert into canzone_main_artists (canzone_id, artista_id) values (501, 1);    -- All the Stars - Kendrick
insert into canzone_main_artists (canzone_id, artista_id) values (501, 51);   -- All the Stars - SZA
insert into canzone_main_artists (canzone_id, artista_id) values (551, 1);    -- Pray for Me - Kendrick
insert into canzone_main_artists (canzone_id, artista_id) values (551, 51);   -- Pray for Me - SZA

-- ================================================
-- CANZONI <-> FEATURING ARTISTS
-- ================================================
insert into canzone_featuring_artists (canzone_id, artista_id) values (51, 51);    -- Die Hard ft. SZA
insert into canzone_featuring_artists (canzone_id, artista_id) values (351, 151);  -- First Person Shooter ft. J. Cole
insert into canzone_featuring_artists (canzone_id, artista_id) values (401, 101);  -- 95 South ft. Drake

-- ================================================
-- PLAYLIST
-- sequence playlist_seq: nextval = 1, 51, 101
-- ================================================
insert into playlist(id, nome, descrizione, autore_id) values(nextval('playlist_seq'), 'Hip Hop Vibes', 'Le migliori tracce rap e hip hop', 51);     -- id=1, autore=user
insert into playlist(id, nome, descrizione, autore_id) values(nextval('playlist_seq'), 'R and B Chill', 'Playlist rilassante R and B', 51);           -- id=51, autore=user
insert into playlist(id, nome, descrizione, autore_id) values(nextval('playlist_seq'), 'Admin Playlist', 'Playlist creata dall amministratore', 1);   -- id=101, autore=admin

-- ================================================
-- PLAYLIST <-> CANZONI
-- ================================================
insert into playlist_canzoni (playlists_id, canzoni_id, posizione) values (1, 1, 0);     -- Hip Hop Vibes - N95
insert into playlist_canzoni (playlists_id, canzoni_id, posizione) values (1, 351, 1);   -- Hip Hop Vibes - First Person Shooter
insert into playlist_canzoni (playlists_id, canzoni_id, posizione) values (1, 451, 2);   -- Hip Hop Vibes - Amari
insert into playlist_canzoni (playlists_id, canzoni_id, posizione) values (51, 151, 0);  -- R&B Chill - Kill Bill
insert into playlist_canzoni (playlists_id, canzoni_id, posizione) values (51, 251, 1);  -- R&B Chill - Open Arms
insert into playlist_canzoni (playlists_id, canzoni_id, posizione) values (51, 501, 2);  -- R&B Chill - All the Stars
insert into playlist_canzoni (playlists_id, canzoni_id, posizione) values (101, 501, 0); -- Admin Playlist - All the Stars
insert into playlist_canzoni (playlists_id, canzoni_id, posizione) values (101, 551, 1); -- Admin Playlist - Pray for Me