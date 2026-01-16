import { AppBar, Toolbar, Typography, Button } from '@mui/material'
import { Link } from 'react-router-dom'

import HomeIcon from '@mui/icons-material/Home';
import HelpIcon from '@mui/icons-material/Help';

export default function NavBar() {
    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" sx={{ flexGrow: 1 }}>
                    Books Store
                </Typography>

                <Button color="inherit" component={Link} to="/" startIcon={<HomeIcon />}>Inicio</Button>
                <Button color="inherit" component={Link} to="/books" startIcon={<HelpIcon />}>Books</Button>
            </Toolbar>
        </AppBar>
    )
}
