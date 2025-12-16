# Proyecto - Base de datos y balanceo

Descripción breve
- Script DDL para crear la base de datos en PostgreSQL con las tablas usadas por la aplicación.
- Puertos y configuración necesaria para que Consul/Traefik y MicroProfile balanceen dos instancias de `app-authors` (8070 y 8071).

## Requisitos
- PostgreSQL (5432)
- Consul agent (8500)
- Traefik (puerto frontal según despliegue, p. ej. 80)
- Dos instancias de `app-authors` activas (puertos 8070 y 8071)
- `app-books` (puerto configurable, p. ej. 8072)

## Crear la base de datos (PostgreSQL)
Ejecutar el siguiente script en el servidor PostgreSQL (p. ej. con `psql`):

```sql
-- TABLE: author
CREATE TABLE author (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    version INTEGER
);

-- TABLE: book
CREATE TABLE book (
    isbn VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255),
    price DOUBLE PRECISION,
    version INTEGER
);

-- TABLE: book_author (N:N)
CREATE TABLE book_author (
    books_isbn VARCHAR(255),
    authors_id BIGINT,
    PRIMARY KEY (books_isbn, authors_id),
    FOREIGN KEY (books_isbn) REFERENCES book(isbn),
    FOREIGN KEY (authors_id) REFERENCES author(id)
);

-- TABLE: inventory
CREATE TABLE inventory (
    book_isbn VARCHAR(255) PRIMARY KEY,
    sold INTEGER,
    supplied INTEGER,
    version INTEGER,
    FOREIGN KEY (book_isbn) REFERENCES book(isbn)
);

-- TABLE: customer
CREATE TABLE customer (
    id BIGINT PRIMARY KEY,
    email VARCHAR(255),
    name VARCHAR(255),
    version INTEGER
);

-- TABLE: purchase_order
CREATE TABLE purchase_order (
    id BIGINT PRIMARY KEY,
    deliveredon TIMESTAMP,
    placedon TIMESTAMP,
    status INTEGER,
    total INTEGER,
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- TABLE: line_item
CREATE TABLE line_item (
    idx INTEGER,
    order_id BIGINT,
    book_isbn VARCHAR(255),
    quantity INTEGER,
    PRIMARY KEY (idx, order_id),
    FOREIGN KEY (order_id) REFERENCES purchase_order(id),
    FOREIGN KEY (book_isbn) REFERENCES book(isbn)
);
