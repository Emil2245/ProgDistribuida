import { useEffect, useState } from "react";
import axios from "axios";
import {
    Container,
    Button,
    Box,
    Typography,
    TextField,
    Stack,
    Grid,
    Table, TableHead, TableRow, TableCell, TableBody
} from "@mui/material";

import { useLocation, useNavigate } from "react-router-dom";

import type { Book } from "../model/Book.ts";
import { apiUrl } from "../config/api.ts";

export default function BooksDetallePage() {

    const url = apiUrl("/books");

    const location = useLocation();
    const navigate = useNavigate();

    const bookInicial: Book = {
        isbn: '',
        title: '',
        price: 0,
        inventorySold: 0,
        inventorySupplied: 0,
        authors: []
    };

    const [isbn] = useState<string>(location.state?.isbn || '');
    const [book, setBook] = useState<Book>(bookInicial);

    const fetchBook = async () => {
        try {
            const response = await axios.get<Book>(url + '/' + isbn);
            setBook(response.data);
        } catch (error) {
            console.error("Error al obtener los posts:", error);
            alert("No se pudieron cargar los datos");
        } finally {
        }
    };

    useEffect(() => {
        fetchBook().then(() => { });
        console.log("render");
    }, [isbn])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setBook({ ...book, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        //alert(book.title)
        axios.put<Book>(url + '/' + isbn, book)
            .then(() => {
                alert('ok');
            })
            .catch(err => {
                alert(err);
            });
    };

    return (
        <Container sx={{ mt: 4 }}>
            <Typography variant="h6">
                Detalle libro: {isbn}
            </Typography>

            <Grid container spacing={3}>
                {/* FORMULARIO - IZQUIERDA */}
                <Grid size={6}>  {/* 6 de 12 columnas */}
                    <Box
                        component="form"
                        onSubmit={handleSubmit}
                        sx={{ width: "100%", maxWidth: 600, mx: "left", mt: 4 }}
                    >
                        <Typography variant="h6" sx={{ mb: 2 }}>
                            Libro
                        </Typography>

                        <TextField
                            label="ISBN"
                            name="isbn"
                            value={book.isbn}
                            onChange={handleChange}
                            fullWidth
                            required
                            margin="normal"
                            disabled={true}
                        />

                        <TextField
                            label="Title"
                            name="title"
                            value={book.title}
                            onChange={handleChange}
                            fullWidth
                            required
                            margin="normal"
                        />

                        <TextField
                            label="Price"
                            name="price"
                            type="number"
                            value={book.price}
                            onChange={handleChange}
                            fullWidth
                            required
                            margin="normal"
                        />

                        <Typography variant="h6" sx={{ mb: 2 }}>
                            Inventario
                        </Typography>

                        <TextField
                            label="Inventario"
                            name="inventorySupplied"
                            type="number"
                            value={book.inventorySupplied ? book.inventorySupplied : 0}
                            onChange={handleChange}
                            fullWidth
                            required
                            margin="normal"
                        />

                        <TextField
                            label="Vendidos"
                            name="price"
                            type="number"
                            value={book.inventorySold ? book.inventorySold : 0}
                            onChange={handleChange}
                            fullWidth
                            required
                            margin="normal"
                            disabled={true}
                        />

                        <Stack spacing={2} direction="row">
                            <Button type="submit" variant="contained">
                                Guardar
                            </Button>
                            <Button onClick={() => navigate('/books')} variant="outlined">
                                Regresar
                            </Button>
                        </Stack>
                    </Box>
                </Grid>
                {/* TABLA - DERECHA */}
                <Grid size={6}>
                    <Box sx={{ width: "100%", maxWidth: 600, mx: "left", mt: 4 }}>
                        <Typography variant="h6" sx={{ mb: 2 }}>
                            Autores
                        </Typography>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>No.</TableCell>
                                    <TableCell>Nombre</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {/* Solo ejemplo, puedes usar otro array */}
                                {book.authors?.map((author, index) => (
                                    <TableRow>
                                        <TableCell>{index + 1}</TableCell>
                                        <TableCell>{author.name}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
}