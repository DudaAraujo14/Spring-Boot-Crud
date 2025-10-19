package com.example.cp2;

import com.example.cp2.domain.Author;
import com.example.cp2.domain.Book;
import com.example.cp2.dto.AuthorRecord;
import com.example.cp2.dto.BookRecord;
import com.example.cp2.mapper.AuthorMapperImpl;
import com.example.cp2.mapper.BookMapperImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapperTests {

    @Test
    void authorMapper_roundtrip() {
        var mapper = new AuthorMapperImpl();
        AuthorRecord record = new AuthorRecord(1L, "Ana", "ana@example.com");

        Author entity = mapper.toEntity(record);
        assertEquals("Ana", entity.getName());

        AuthorRecord back = mapper.toRecord(entity);
        assertEquals("ana@example.com", back.email());
    }

    @Test
    void bookMapper_includesAuthorId() {
        var mapper = new BookMapperImpl();
        var author = new Author(10L, "Ana", "ana@example.com");
        var book = new Book(5L, "Livro Teste", "ISBN-001", author);

        BookRecord record = mapper.toRecord(book);
        assertEquals(10L, record.authorId());
        assertEquals("Livro Teste", record.title());
    }
}
