import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import theme from "./theme/theme";
import AdminAlbumEdit from "./pages/AdminAlbumEdit";
import UserPlaylistEdit from "./pages/UserPlaylistEdit";

function App() {
    return (
        <ThemeProvider theme={theme}>
            <BrowserRouter>
                <Routes>
                    <Route path="/admin/albums/:id/edit" element={<AdminAlbumEdit />} />
                    <Route path="/user/playlists/:id/edit" element={<UserPlaylistEdit />} />
                </Routes>
            </BrowserRouter>
        </ThemeProvider>
    );
}

export default App;