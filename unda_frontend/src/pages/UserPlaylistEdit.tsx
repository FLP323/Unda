import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import {
    TextField, Button, Box, Typography, List, ListItem, ListItemText, IconButton,
    InputBase, Autocomplete
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { DragDropContext, Droppable, Draggable } from "@hello-pangea/dnd";
import api from "../services/api";

interface Song {
    id: number;
    titolo: string;
    durata: number;
}

const UserPlaylistEdit: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [nome, setNome] = useState("");
    const [descrizione, setDescrizione] = useState("");
    const [playlistSongs, setPlaylistSongs] = useState<Song[]>([]);
    const [search, setSearch] = useState("");
    const [results, setResults] = useState<Song[]>([]);

    useEffect(() => {
        api.get(`/rest/playlists/${id}`).then(res => {
            setNome(res.data.nome);
            setDescrizione(res.data.descrizione);
            setPlaylistSongs(res.data.canzoni);
        });
    }, [id]);

    const handleSearch = async () => {
        const res = await api.get("/rest/canzoni/search", { params: { q: search } });
        setResults(res.data);
    };

    const addToPlaylist = async (song: Song) => {
        await api.post(`/rest/user/playlists/${id}/songs/${song.id}`);
        setPlaylistSongs([...playlistSongs, song]);
    };

    const removeFromPlaylist = async (songId: number) => {
        await api.delete(`/rest/user/playlists/${id}/songs/${songId}`);
        setPlaylistSongs(playlistSongs.filter(s => s.id !== songId));
    };

    const onDragEnd = async (result: any) => {
        if (!result.destination) return;
        const items = Array.from(playlistSongs);
        const [reordered] = items.splice(result.source.index, 1);
        items.splice(result.destination.index, 0, reordered);
        setPlaylistSongs(items);
        // Salva l'ordine: PUT con l'array nel nuovo ordine
        await api.put(`/rest/user/playlists/${id}`, {
            nome, descrizione,
            canzoni: items.map(s => ({ id: s.id }))
        });
    };

    const handleSave = async () => {
		await api.put(`/rest/user/playlists/${id}`, { nome, descrizione, canzoni: playlistSongs });
		window.location.replace("http://127.0.0.1:8080/user/playlists");
    };

    return (
        <Box sx={{ maxWidth: 800, mx: "auto", mt: 3 }}>
            <Typography variant="h4">Modifica Playlist</Typography>
            <TextField fullWidth label="Nome" value={nome} onChange={e => setNome(e.target.value)} margin="normal" />
            <TextField fullWidth label="Descrizione" value={descrizione} onChange={e => setDescrizione(e.target.value)} margin="normal" multiline />

            <Typography variant="h6" mt={3}>Aggiungi canzoni</Typography>
            <Box display="flex" mb={2}>
                <InputBase fullWidth placeholder="Cerca..." value={search}
                    onChange={e => setSearch(e.target.value)}
                    onKeyPress={e => e.key === "Enter" && handleSearch()}
                    sx={{ border: "1px solid #ccc", borderRadius: 1, p: 0.5 }} />
                <Button variant="contained" onClick={handleSearch}>Cerca</Button>
            </Box>
            <List>
                {results.map(song => (
                    <ListItem key={song.id} secondaryAction={
                        <Button onClick={() => addToPlaylist(song)}>+</Button>
                    }>
                        <ListItemText primary={song.titolo}
                            secondary={`${Math.floor(song.durata / 60)}:${(song.durata % 60).toString().padStart(2, "0")}`} />
                    </ListItem>
                ))}
            </List>

            <Typography variant="h6" mt={3}>Canzoni nella playlist</Typography>
            <DragDropContext onDragEnd={onDragEnd}>
                <Droppable droppableId="playlistSongs">
                    {(provided) => (
                        <List {...provided.droppableProps} ref={provided.innerRef}>
                            {playlistSongs.map((song, index) => (
                                <Draggable key={song.id} draggableId={song.id.toString()} index={index}>
                                    {(provided) => (
                                        <ListItem ref={provided.innerRef} {...provided.draggableProps} {...provided.dragHandleProps}
                                            secondaryAction={
                                                <IconButton onClick={() => removeFromPlaylist(song.id)}>
                                                    <DeleteIcon />
                                                </IconButton>
                                            }>
                                            <ListItemText primary={song.titolo} />
                                        </ListItem>
                                    )}
                                </Draggable>
                            ))}
                            {provided.placeholder}
                        </List>
                    )}
                </Droppable>
            </DragDropContext>

            <Button variant="contained" onClick={handleSave} sx={{ mt: 3 }}>Salva</Button>
        </Box>
    );
};

export default UserPlaylistEdit;