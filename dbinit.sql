CREATE DATABASE IF NOT EXISTS grclone_db;

USE grclone_db;

CREATE TABLE books(
    isbn VARCHAR(17) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    PRIMARY KEY(isbn)
);

INSERT INTO books(isbn, title, author) VALUES ("9780063081918", "American Gods", "Neil Gaiman");
INSERT INTO books(isbn, title, author) VALUES ("9781101530641", "The Haunting of Hill House", "Shirley Jackson");
INSERT INTO books(isbn, title, author) VALUES ("9780316066525", "Infinite Jest", "David Foster Wallace");
