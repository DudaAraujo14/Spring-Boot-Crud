package com.example.cp2.service;

import com.example.cp2.domain.Author;
import com.example.cp2.dto.AuthorRecord;
import com.example.cp2.exception.ResourceNotFoundException;
import com.example.cp2.mapper.AuthorMapper;
import com.example.cp2.repository.AuthorRepository;
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

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository repository;

    @Mock
    private AuthorMapper mapper;

    @InjectMocks
    private AuthorServiceImpl service;

    private Author author;
    private AuthorRecord record;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Robert Martin");
        author.setEmail("clean@code.com");

        record = new AuthorRecord(1L, "Robert Martin", "clean@code.com");
    }

    @Test
    void shouldCreateAuthor() {
        when(mapper.toEntity(any(AuthorRecord.class))).thenReturn(author);
        when(repository.save(any(Author.class))).thenReturn(author);
        when(mapper.toRecord(any(Author.class))).thenReturn(record);

        AuthorRecord result = service.create(record);

        assertNotNull(result);
        assertEquals("Robert Martin", result.name());
    }

    @Test
    void shouldFindAuthorById() {
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(mapper.toRecord(author)).thenReturn(record);

        AuthorRecord result = service.findById(1L);

        assertEquals("Robert Martin", result.name());
    }

    @Test
    void shouldThrowExceptionWhenAuthorNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void shouldReturnAllAuthors() {
        when(repository.findAll()).thenReturn(List.of(author));
        when(mapper.toRecord(author)).thenReturn(record);

        List<AuthorRecord> result = service.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldUpdateAuthor() {
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(repository.save(any(Author.class))).thenReturn(author);
        when(mapper.toRecord(any(Author.class))).thenReturn(record);

        AuthorRecord updated = service.update(1L, record);

        assertEquals("Robert Martin", updated.name());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentAuthor() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, record));
    }

    @Test
    void shouldDeleteAuthor() {

        when(repository.existsById(1L)).thenReturn(true);

        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
