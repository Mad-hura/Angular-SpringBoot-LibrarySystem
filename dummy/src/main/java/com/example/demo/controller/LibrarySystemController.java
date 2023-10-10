package com.example.demo.controller;


import com.example.demo.dao.IBookDao;
import com.example.demo.model.Book;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/book")
@Slf4j
@Builder
public class LibrarySystemController {
    private final IBookDao bookDao;

    @Autowired
    public LibrarySystemController(IBookDao bookDao) {

        this.bookDao = bookDao;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = (List<Book>) bookDao.findAll();
        return ResponseEntity.ok(books);
    }
    @GetMapping("/{bookId}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable Long bookId) {
        log.info("Fetching Book by Id");
        Optional<Book> book = bookDao.findById(bookId);
        try {
            if (book != null) {
                return ResponseEntity.ok(book);
            } else
                throw new Exception();
        }catch(Exception e){
            log.error("Book with Id not found");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        log.info("Creating a Book");
        Book book1 = Book.builder().bookId(book.getBookId()).genre(book.getGenre()).title(book.getTitle()).author(book.getAuthor()).build();
        Book createdBook = bookDao.save(book1);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook) {
      log.info("Updating Title and Genre of Book");
        Optional<Book> existingBookOptional = bookDao.findById(bookId);

        try
        {   if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setGenre(updatedBook.getGenre());

            Book updated = bookDao.save(existingBook);
            return ResponseEntity.ok(updated);
            }
            else throw new Exception();
        }catch(Exception e ){
            log.error("Unable to Update Book Title");
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
       log.info("Deleting Book By Id");
        Optional<Book> book = bookDao.findById(bookId);
        try {
            if (book.isPresent()) {
                bookDao.deleteById(bookId);
                return ResponseEntity.noContent().build();
            } else
                throw new Exception();
        }catch (Exception e){
            log.error("Unable to Delete Book");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-genre")
    public List<Book> getBooksByGenre(@RequestParam("genre")String genre){
       log.info("Getting books by Genre");
        return bookDao.findByGenreIgnoreCase(genre);
    }

    @GetMapping("/by-partial-title")
    public List<Book> getBooksByParticaularTitle(@RequestParam("title") String title){
        return bookDao.findByPartialTitleIgnoreCase(title);
    }
}
