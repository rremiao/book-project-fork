package com.karankumar.bookproject.statistics;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;


import java.util.Set;
import java.util.function.BinaryOperator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karankumar.bookproject.book.dto.BookDto;
import com.karankumar.bookproject.book.model.Book;
import com.karankumar.bookproject.book.model.BookGenre;
import com.karankumar.bookproject.book.repository.BookRepository;

@Service
public class StatisticsService {
    
    @Autowired
    BookRepository bookRepository;

    @Autowired
    ModelMapper mapper;

    public BookDto mostLikedBook() {
        List<Book> allBooks = bookRepository.findAll();

        Book book = allBooks.get(0);

        return mapper.map(book, BookDto.class);
    }

    public BookDto leastLikedBook() {
        List<Book> allBooks = bookRepository.findAll();

        Book book = allBooks.get(0);

        return mapper.map(book, BookDto.class);
    }

    public BookDto longestReadBook() {
        List<Book> allBooks = bookRepository.findAll();

        Book book = allBooks.get(0);

        return mapper.map(book, BookDto.class);
    }

    public BookGenre mostReadGenre() {
        List<Book> allBooks = bookRepository.findAll();
        Set<BookGenre> bookGenres = new HashSet<>();

        for(Book book : allBooks) {
            bookGenres.addAll(book.getBookGenre());
        }

        return bookGenres.stream()
                        .reduce(BinaryOperator.maxBy((o1, o2) 
                        -> Collections.frequency(bookGenres, o1) - Collections.frequency(bookGenres, o2)))
                        .orElse(null);       
        
    }
}
