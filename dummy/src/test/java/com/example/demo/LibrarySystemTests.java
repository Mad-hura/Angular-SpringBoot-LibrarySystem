package com.example.demo;

import com.example.demo.dao.IBookDao;
import com.example.demo.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibrarySystemTests {

    @Autowired
    private IBookDao bookDao;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveBookTest(){
        Book book = Book.builder().title("Dare To Lead").genre("Work").author("Brene Brown").build();
        bookDao.save(book);

        Assertions.assertThat(book.getBookId()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void getBookTest(){
        List<Book> books = (List<Book>) bookDao.findAll();
        Assertions.assertThat(books.size() >0);

    }

    @Test
    @Order(2)
    public void getBookByIdTest(){
        Book book = bookDao.findById(1L).get();
        Assertions.assertThat(book.getBookId()) .isEqualTo(1L);
    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateTest(){
        Book book = bookDao.findById(1L).get();
        book.setGenre("Learning");
        Book updated = bookDao.save(book);
        Assertions.assertThat(updated.getGenre()).isEqualTo("Learning");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void getBookByGenreTest(){
        List<Book> book = (List<Book>) bookDao.findByGenreIgnoreCase("Learning");
        Assertions.assertThat(book.size()==1);

    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void deleteTest(){
        Book book = bookDao.findById(1L).get();
        bookDao.deleteById(1L);

        Book book1 = null;
        Optional<Book> optionalBook = bookDao.findByAuthor("Brene Brown");
        if(optionalBook.isPresent()){
            book1=optionalBook.get();
        }
        Assertions.assertThat(book1).isNull();

    }


}
