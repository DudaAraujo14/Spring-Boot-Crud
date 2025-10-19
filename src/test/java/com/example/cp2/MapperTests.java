package com.example.cp2;

import com.example.cp2.domain.Author;
import com.example.cp2.domain.Book;
import com.example.cp2.dto.AuthorRecord;
import com.example.cp2.dto.BookRecord;
import com.example.cp2.mapper.AuthorMapperImpl;
import com.example.cp2.mapper.BookMapperImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTests {

    private final AuthorMapperImpl authorMapper = new AuthorMapperImpl();
    private final BookMapperImpl bookMapper = new BookMapperImpl();

    @Test
    void shouldMapAuthorToEntityAndBack() {
        AuthorRecord record = new AuthorRecord(1L, "Robert Martin", "clean@code.com");
        Author entity = authorMapper.toEntity(record);

        assertEquals("Robert Martin", entity.getName());
        assertEquals("clean@code.com", entity.getEmail());

        AuthorRecord back = authorMapper.toRecord(entity);
        assertEquals("Robert Martin", back.name());
        assertEquals("clean@code.com", back.email());
    }

    @Test
    void shouldMapBookToEntityAndBack() {
        // book record agora com 4 argumentos (id, title, isbn, authorId)
        BookRecord record = new BookRecord(1L, "Clean Code", "123456", 10L);
        Book entity = bookMapper.toEntity(record);

        assertEquals("Clean Code", entity.getTitle());
        assertEquals("123456", entity.getIsbn());

        BookRecord back = bookMapper.toRecord(entity);
        assertEquals("Clean Code", back.title());
        assertEquals("123456", back.isbn());
    }
}
