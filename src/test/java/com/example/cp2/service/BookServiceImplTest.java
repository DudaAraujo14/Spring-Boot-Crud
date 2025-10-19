package com.example.cp2.service;

import com.example.cp2.domain.Book;
import com.example.cp2.dto.BookRecord;
import com.example.cp2.exception.ResourceNotFoundException;
import com.example.cp2.mapper.BookMapper;
import com.example.cp2.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository repository;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookServiceImpl service;

    private Book book;
    private BookRecord record;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setIsbn("123456");

        record = new BookRecord(1L, "Clean Code", "123456", 10L);
    }

    @Test
    void shouldCreateBook() {
        when(mapper.toEntity(any(BookRecord.class))).thenReturn(book);
        when(repository.save(any(Book.class))).thenReturn(book);
        when(mapper.toRecord(any(Book.class))).thenReturn(record);

        BookRecord result = service.create(record);

        assertNotNull(result);
        assertEquals("Clean Code", result.title());
    }

    @Test
    void shouldFindBookById() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(mapper.toRecord(book)).thenReturn(record);

        BookRecord result = service.findById(1L);

        assertEquals("Clean Code", result.title());
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void shouldReturnAllBooks() {
        when(repository.findAll()).thenReturn(List.of(book));
        when(mapper.toRecord(book)).thenReturn(record);

        List<BookRecord> result = service.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldUpdateBook() {
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(any(Book.class))).thenReturn(book);
        when(mapper.toRecord(any(Book.class))).thenReturn(record);

        BookRecord updated = service.update(1L, record);

        assertEquals("Clean Code", updated.title());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentBook() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, record));
    }

    @Test
    void shouldDeleteBook() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldFindBooksByAuthorId() {
        when(repository.findByAuthorId(1L)).thenReturn(List.of(book));
        when(mapper.toRecord(book)).thenReturn(record);

        List<BookRecord> result = service.findByAuthorId(1L);

        assertEquals(1, result.size());
    }
}
