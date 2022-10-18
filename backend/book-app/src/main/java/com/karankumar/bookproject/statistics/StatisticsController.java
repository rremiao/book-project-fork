package com.karankumar.bookproject.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.karankumar.bookproject.Mappings;
import com.karankumar.bookproject.book.dto.BookDto;
import com.karankumar.bookproject.book.model.BookGenre;

@RestController
@RequestMapping(Mappings.STATISTICS)
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    public static class Endpoints {
        private Endpoints() {}
    
        public static final String MOST_LIKED_BOOK = "/books/mostLiked";
        public static final String LEAST_LIKED_BOOK = "/books/leastLiked";
        public static final String LONGEST_READ_BOOK = "/books/longest";
        public static final String MOST_READ_GENRE = "/genre/read";
        public static final String MOST_LIKED_GENRE = "/genre/liked";
        public static final String AVERAGE_RATING_READ = "/rating/averageRead";
        public static final String AVERAGE_PAGE_LENGTH = "/page/averageLength";
      }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(Endpoints.MOST_LIKED_BOOK)
    public BookDto mostLikedBook() {
        return statisticsService.mostLikedBook();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(Endpoints.LEAST_LIKED_BOOK)
    public BookDto leastLikedBooks() {
        return statisticsService.leastLikedBook();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(Endpoints.LONGEST_READ_BOOK)
    public BookDto longestReadBook() {
        return statisticsService.longestReadBook();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(Endpoints.MOST_READ_GENRE)
    public BookGenre mostReadGenre() {
        return statisticsService.mostReadGenre();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(Endpoints.MOST_LIKED_GENRE)
    public BookGenre mostLikedGenre() {
        return statisticsService.mostLikedGenre();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(Endpoints.AVERAGE_RATING_READ)
    public Double averageRatingRead() {
        return statisticsService.averageRatingRead();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(Endpoints.AVERAGE_PAGE_LENGTH)
    public Double averagePageLength() {
        return statisticsService.averagePageLenth();
    }    
}