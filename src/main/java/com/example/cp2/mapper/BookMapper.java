package com.example.cp2.mapper;

import com.example.cp2.domain.Book;
import com.example.cp2.dto.BookRecord;

public interface BookMapper {
    BookRecord toRecord(Book entity);
    Book toEntity(BookRecord record);
}
