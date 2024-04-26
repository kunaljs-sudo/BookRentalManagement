package com.deom.BookManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deom.BookManagementSystem.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
