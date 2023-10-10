package com.example.demo.dao;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookDao  extends CrudRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.genre)=LOWER(:genre)")
    List<Book> findByGenreIgnoreCase(String genre);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByPartialTitleIgnoreCase(String title);



    Optional<Book> findByAuthor(String author);

}
