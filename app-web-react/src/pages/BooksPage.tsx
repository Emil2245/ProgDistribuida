import {useEffect, useState} from "react";
import axios from "axios";
import {
    Container,
    Button,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography,
    CircularProgress,
} from "@mui/material";

import {useNavigate} from "react-router-dom";

import type {Book} from "../model/Book.ts";
import { apiUrl } from "../config/api.ts";

export default function BooksPage() {
    const [books, setBooks] = useState<Book[]>([]);
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    const fetchBooks = async () => {
        setLoading(true);
        try {
            const response = await axios.get<Book[]>(
                apiUrl("/books")
            );
            setBooks(response.data);
        } catch (error) {
            console.error("Error al obtener los posts:", error);
            alert("No se pudieron cargar los datos");
        } finally {
            setLoading(false);
        }
    };

    const verLibro = (e: React.MouseEvent<HTMLAnchorElement>) => {
        e.preventDefault();

        const miEstado = {
            isbn: e.currentTarget.textContent
        }

        navigate('/books-detalle', {state: miEstado})
    }

    useEffect(() => {
        fetchBooks();
    }, [])

    return (
        <Container sx={{mt: 4}}>
            <Typography variant="h5" gutterBottom>
                Libros disponibles
            </Typography>

            <Button
                variant="contained"
                color="primary"
                onClick={fetchBooks}
                disabled={loading}
            >
                {loading ? "Cargando..." : "Cargar datos"}
            </Button>

            {loading && <CircularProgress sx={{ml: 2}}/>}

            <Table sx={{mt: 3}}>
                <TableHead>
                    <TableRow>
                        <TableCell>ISBN</TableCell>
                        <TableCell>Título</TableCell>
                        <TableCell>Precio</TableCell>
                        {/*<TableCell>Autores</TableCell>*/}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {books.map((book) => (
                        <TableRow key={book.isbn}>
                            <TableCell>
                                <a href='#' onClick={verLibro}>
                                    {book.isbn}
                                </a>
                            </TableCell>
                            <TableCell>{book.title}</TableCell>
                            <TableCell>{book.price}</TableCell>
                            {/*<TableCell>*/}
                            {/*    {book.authors?.map((author, index) => (*/}
                            {/*        <span key={index}>{author.name}, </span>*/}
                            {/*    ))}*/}
                            {/*</TableCell>*/}
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Container>
    );
}