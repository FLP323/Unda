import { createTheme } from "@mui/material/styles";

const theme = createTheme({
    palette: {
        primary: { main: "#1a1a2e" },
        secondary: { main: "#0f3460" },
    },
    typography: {
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    },
});

export default theme;