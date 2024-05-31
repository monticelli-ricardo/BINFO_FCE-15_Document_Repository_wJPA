-- mysql -u username -p javaee < db_dump.sql

-- Create the database
CREATE DATABASE IF NOT EXISTS `javaee`;
USE `javaee`;

-- Create the authors table
CREATE TABLE `authors` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

-- Create the documents table
CREATE TABLE `documents` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `abstractText` TEXT,
    `publicationDate` DATE,
    `storageLocation` VARCHAR(255),
    `publisher` VARCHAR(255),
    PRIMARY KEY (`id`)
);

-- Create the junction table for the many-to-many relationship between documents and authors
CREATE TABLE `document_authors` (
    `document_id` BIGINT NOT NULL,
    `author_id` BIGINT NOT NULL,
    FOREIGN KEY (`document_id`) REFERENCES `documents`(`id`),
    FOREIGN KEY (`author_id`) REFERENCES `authors`(`id`),
    PRIMARY KEY (`document_id`, `author_id`)
);

-- Insert authors
INSERT INTO `authors` (`name`) VALUES
('J.K. Rowling'),
('George Orwell'),
('J.R.R. Tolkien'),
('Agatha Christie'),
('Stephen King'),
('Mark Twain'),
('Jane Austen'),
('Charles Dickens'),
('F. Scott Fitzgerald'),
('Ernest Hemingway');

-- Insert documents
INSERT INTO `documents` (`title`, `abstractText`, `publicationDate`, `storageLocation`, `publisher`) VALUES
('Harry Potter and the Philosopher''s Stone', 'Fantasy novel about a young wizard named Harry Potter', '1997-06-26', 'Shelf A1', 'Bloomsbury'),
('1984', 'Dystopian social science fiction novel and cautionary tale about the future', '1949-06-08', 'Shelf B2', 'Secker & Warburg'),
('The Hobbit', 'Fantasy novel about the adventures of a hobbit named Bilbo Baggins', '1937-09-21', 'Shelf C3', 'George Allen & Unwin'),
('Murder on the Orient Express', 'Detective novel featuring Hercule Poirot', '1934-01-01', 'Shelf D4', 'Collins Crime Club'),
('The Shining', 'Horror novel about a haunted hotel and its caretaker''s descent into madness', '1977-01-28', 'Shelf E5', 'Doubleday'),
('The Adventures of Tom Sawyer', 'Novel about a young boy growing up along the Mississippi River', '1876-06-01', 'Shelf F6', 'American Publishing Company'),
('Pride and Prejudice', 'Romantic novel about the manners and matrimonial machinations among the British gentry of the early 19th century', '1813-01-28', 'Shelf G7', 'T. Egerton, Whitehall'),
('A Tale of Two Cities', 'Historical novel set in London and Paris before and during the French Revolution', '1859-04-30', 'Shelf H8', 'Chapman & Hall'),
('The Great Gatsby', 'Novel about the American dream and the roaring twenties', '1925-04-10', 'Shelf I9', 'Charles Scribner''s Sons'),
('The Old Man and the Sea', 'Novel about an aging fisherman''s struggle to catch a giant marlin', '1952-09-01', 'Shelf J10', 'Charles Scribner''s Sons');

-- Insert document_authors
INSERT INTO `document_authors` (`document_id`, `author_id`) VALUES
(1, 1),  -- J.K. Rowling
(2, 2),  -- George Orwell
(3, 3),  -- J.R.R. Tolkien
(4, 4),  -- Agatha Christie
(5, 5),  -- Stephen King
(6, 6),  -- Mark Twain
(7, 7),  -- Jane Austen
(8, 8),  -- Charles Dickens
(9, 9),  -- F. Scott Fitzgerald
(10, 10);  -- Ernest Hemingway
