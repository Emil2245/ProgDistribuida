import {Typography, Container} from '@mui/material';

export default function Home() {
    return (
        <>
            <Container sx={{mt: 4}}>
                <Typography variant="h4" gutterBottom>
                    Bienvenido
                </Typography>
                <Typography>
                    Aplicación creada con React + TypeScript + Router y Material UI.
                </Typography>
                <br/>
            </Container>
        </>
    );
}
