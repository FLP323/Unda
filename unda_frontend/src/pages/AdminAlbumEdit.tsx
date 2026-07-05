import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import {
    TextField, Button, Box, Typography, Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow, Paper, IconButton, Autocomplete,
    Alert
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import api from "../services/api";

interface Artist {
    id: number;
    name: string;
}

interface Song {
    id?: number;
    titolo: string;
    durata: number;
    numeroTraccia: number;
    mainArtists: Artist[];
    featuringArtists: Artist[];
}

const AdminAlbumEdit: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [titolo, setTitolo] = useState("");
    const [releaseDate, setReleaseDate] = useState("");
    const [artisti, setArtisti] = useState<Artist[]>([]);
    const [songs, setSongs] = useState<Song[]>([]);
    const [allArtists, setAllArtists] = useState<Artist[]>([]);
    const [saved, setSaved] = useState(false);

    useEffect(() => {
        api.get(`/rest/albums/${id}`)
            .then(res => {
                const album = res.data;
                setTitolo(album.titolo);
                setReleaseDate(album.releaseDate);
                setArtisti(album.artisti || []);
                setSongs(album.canzoni.map((c: any) => ({
                    id: c.id,
                    titolo: c.titolo,
                    durata: c.durata,
                    numeroTraccia: c.numeroTraccia,
                    mainArtists: c.mainArtists || [],
                    featuringArtists: c.featuringArtists || [],
                })));
            })
            .catch(err => console.error("Errore nel caricamento album:", err));

        api.get("/rest/artists")
            .then(res => setAllArtists(res.data))
            .catch(() => setAllArtists([]));
    }, [id]);

    const handleSongChange = (index: number, field: keyof Song, value: any) => {
        const newSongs = [...songs];
        (newSongs[index] as any)[field] = value;
        setSongs(newSongs);
    };

    const addSong = () => {
        setSongs([...songs, {
            titolo: "", durata: 0, numeroTraccia: songs.length + 1,
            mainArtists: [], featuringArtists: []
        }]);
    };

    const removeSong = (index: number) => {
        const newSongs = songs.filter((_, i) => i !== index);
        newSongs.forEach((s, i) => s.numeroTraccia = i + 1);
        setSongs(newSongs);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // Validazione rapida
        for (let i = 0; i < songs.length; i++) {
            if (!songs[i].titolo.trim()) {
                alert(`La canzone #${songs[i].numeroTraccia} non ha un titolo.`);
                return;
            }
            if (songs[i].durata <= 0) {
                alert(`La canzone #${songs[i].numeroTraccia} non ha una durata valida.`);
                return;
            }
            if (songs[i].mainArtists.length === 0) {
                alert(`La canzone #${songs[i].numeroTraccia} deve avere almeno un artista principale.`);
                return;
            }
        }

        const albumData = {
            titolo,
            releaseDate,
            artisti: artisti.map(a => ({ id: a.id })),
            canzoni: songs.map(s => ({
                titolo: s.titolo,
                durata: s.durata,
                numeroTraccia: s.numeroTraccia,
                mainArtists: s.mainArtists.map(a => ({ id: a.id })),
                featuringArtists: s.featuringArtists.map(a => ({ id: a.id })),
            }))
        };

        try {
            await api.put(`/rest/admin/albums/${id}`, albumData);
            setSaved(true);
        } catch (err) {
            console.error("Errore nel salvataggio:", err);
            alert("Si è verificato un errore durante il salvataggio.");
        }
    };

    if (saved) {
        return (
            <Box sx={{ maxWidth: 600, mx: "auto", mt: 5, textAlign: "center" }}>
                <Alert severity="success" sx={{ mb: 2 }}>
                    Album salvato con successo!
                </Alert>
                <Button variant="contained" href={`http://localhost:8080/albums/${id}`}>
                    Vai all'album
                </Button>
            </Box>
        );
    }

    return (
        <Box sx={{ maxWidth: 900, mx: "auto", mt: 3 }}>
            <Typography variant="h4" mb={2}>Modifica Album</Typography>

            <TextField fullWidth label="Titolo" value={titolo}
                onChange={e => setTitolo(e.target.value)} margin="normal" />
            <TextField fullWidth type="date" label="Data uscita" value={releaseDate}
                onChange={e => setReleaseDate(e.target.value)} margin="normal"
                slotProps={{ inputLabel: { shrink: true } }} />

            <Typography variant="h6" mt={2}>Artisti</Typography>
            <Box sx={{ display: 'flex', gap: 1, flexWrap: 'wrap', mb: 2 }}>
                {artisti.map(a => (
                    <Typography key={a.id} sx={{ backgroundColor: '#e0e0e0', px: 1.5, py: 0.5, borderRadius: 1 }}>
                        {a.name}
                    </Typography>
                ))}
            </Box>
            <Typography variant="caption" color="textSecondary">
                (per modificare gli artisti usa la pagina Thymeleaf)
            </Typography>

            <Typography variant="h6" mt={3}>Canzoni</Typography>
            <TableContainer component={Paper} sx={{ mt: 1 }}>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell width={40}>#</TableCell>
                            <TableCell width={250}>Titolo</TableCell>
                            <TableCell width={110}>Durata (s)</TableCell>
                            <TableCell width={250}>Main Artists</TableCell>
                            <TableCell width={250}>Featuring</TableCell>
                            <TableCell width={50}></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {songs.map((song, index) => (
                            <TableRow key={index}>
                                <TableCell>{song.numeroTraccia}</TableCell>
                                <TableCell>
                                    <TextField size="small" fullWidth
                                        value={song.titolo}
                                        onChange={e => handleSongChange(index, "titolo", e.target.value)}
                                        placeholder="Titolo" />
                                </TableCell>
                                <TableCell>
                                    <TextField size="small" type="number" fullWidth
                                        value={song.durata === 0 ? "" : song.durata}
                                        onChange={e => {
                                            const val = e.target.value === "" ? 0 : parseInt(e.target.value) || 0;
                                            handleSongChange(index, "durata", val);
                                        }}
                                        inputprops={{ min: 0 }} />
                                </TableCell>
                                <TableCell>
                                    <Autocomplete multiple size="small"
                                        options={allArtists}
                                        getOptionLabel={(opt: Artist) => opt.name}
                                        value={song.mainArtists}
                                        onChange={(_, newVal) => handleSongChange(index, "mainArtists", newVal)}
                                        isOptionEqualToValue={(opt, val) => opt.id === val.id}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </TableCell>
                                <TableCell>
                                    <Autocomplete multiple size="small"
                                        options={allArtists}
                                        getOptionLabel={(opt: Artist) => opt.name}
                                        value={song.featuringArtists}
                                        onChange={(_, newVal) => handleSongChange(index, "featuringArtists", newVal)}
                                        isOptionEqualToValue={(opt, val) => opt.id === val.id}
                                        renderInput={(params) => <TextField {...params} />}
                                    />
                                </TableCell>
                                <TableCell>
                                    <IconButton onClick={() => removeSong(index)}>
                                        <DeleteIcon />
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <Button variant="outlined" onClick={addSong} sx={{ mt: 2 }}>
                + Aggiungi canzone
            </Button>

            <Box sx={{ mt: 3 }}>
                <Button variant="contained" onClick={handleSubmit} size="large">
                    Salva Album
                </Button>
            </Box>
        </Box>
    );
};

export default AdminAlbumEdit;