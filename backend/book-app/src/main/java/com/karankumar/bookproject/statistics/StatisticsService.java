package com.karankumar.bookproject.statistics;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

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
    RatingStatistics ratingStatistics;

    @Autowired
    ModelMapper mapper;

    public BookDto mostLikedBook() {
        Optional<Book> book = ratingStatistics.findMostLikedBook();

        if(!book.isPresent()) return null;

        return mapper.map(book, BookDto.class);
    }

    public BookDto leastLikedBook() {
        Optional<Book> book = ratingStatistics.findLeastLikedBook();

        if(!book.isPresent()) return null;

        return mapper.map(book, BookDto.class);
    }

    public BookDto longestReadBook() {
        List<Book> allBooks = bookRepository.findAll();

        Book book = allBooks.stream().sorted(Comparator.comparing(Book::getPagesRead)).findFirst().get();

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

    public BookGenre mostLikedGenre() {
        List<Book> allBooks = bookRepository.findAll();
        Set<BookGenre> bookGenres = new HashSet<>();

        allBooks = allBooks.stream().sorted(Comparator.comparing(Book::getRating)).collect(Collectors.toList());

        for(Book book : allBooks) {
            bookGenres.addAll(book.getBookGenre());
        }

        return bookGenres.stream()
                         .reduce(BinaryOperator.maxBy((o1, o2) 
                         -> Collections.frequency(bookGenres, o1) - Collections.frequency(bookGenres, o2)))
                         .orElse(null);      
    }

    public Double averageRatingRead() {
        List<Book> allBooks = bookRepository.findAll();

        return allBooks.stream().collect(Collectors.averagingDouble(p -> p.getRating().getValue()));
    }

    public Double averagePageLenth() {
        List<Book> allBooks = bookRepository.findAll();

        return allBooks.stream().filter(x -> x.getRating() != null).collect(Collectors.averagingDouble(Book::getNumberOfPages));
    }
}
