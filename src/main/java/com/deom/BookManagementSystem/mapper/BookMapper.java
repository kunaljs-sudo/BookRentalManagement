package com.deom.BookManagementSystem.mapper;

import com.deom.BookManagementSystem.dto.BookDTO;
import com.deom.BookManagementSystem.entity.Book;
import com.deom.BookManagementSystem.exception.BadDataProvidedException;

public class BookMapper {
    public static BookDTO mapBook2DTO(Book book) {
        if (book == null) {
            throw new BadDataProvidedException("Book Object Provided is null");
        }

        return BookDTO.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .availabilityStatus(book.isAvailabilityStatus())
                .userEmail(book.getRentedBy() == null ? "" : book.getRentedBy().getEmail())
                .build();
    }
}
