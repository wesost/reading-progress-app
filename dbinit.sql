CREATE DATABASE IF NOT EXISTS grclone_db;

USE grclone_db;

CREATE TABLE authors(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE books(
    isbn VARCHAR(17) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT,
    image_url VARCHAR(255),
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

INSERT INTO authors(name) VALUES ("Neil Gaiman");
INSERT INTO authors(name) VALUES ("Shirley Jackson");
INSERT INTO authors(name) VALUES ("David Foster Wallace");

INSERT INTO books(isbn, title, author_id, image_url) VALUES ("9780063081918", "American Gods", 1, "https://test.com");
INSERT INTO books(isbn, title, author_id, image_url) VALUES ("9781101530641", "The Haunting of Hill House", 2, "https://hhh-coverimage.com");
INSERT INTO books(isbn, title, author_id, image_url) VALUES ("9780316066525", "Infinite Jest", 3, "http://localhost:8080/books/9780316066525");
