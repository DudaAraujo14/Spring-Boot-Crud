package com.example.cp2.service;

import com.example.cp2.dto.BookRecord;
import java.util.List;

public interface BookService {
    BookRecord create(BookRecord record);
    BookRecord findById(Long id);
    List<BookRecord> findAll();
    BookRecord update(Long id, BookRecord record);
    void delete(Long id);
    List<BookRecord> findByAuthorId(Long authorId);
}
