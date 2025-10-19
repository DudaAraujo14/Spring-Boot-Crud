package com.example.cp2.mapper;

import com.example.cp2.domain.Author;
import com.example.cp2.domain.Book;
import com.example.cp2.dto.BookRecord;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookRecord toRecord(Book e) {
        if (e == null) return null;
        Long authorId = e.getAuthor() != null ? e.getAuthor().getId() : null;
        return new BookRecord(e.getId(), e.getTitle(), e.getIsbn(), authorId);
    }
    @Override
    public Book toEntity(BookRecord r) {
        if (r == null) return null;
        Book b = new Book();
        b.setId(r.id());
        b.setTitle(r.title());
        b.setIsbn(r.isbn());
        if (r.authorId() != null) {
            Author a = new Author();
            a.setId(r.authorId());
            b.setAuthor(a);
        }
        return b;
    }
}
