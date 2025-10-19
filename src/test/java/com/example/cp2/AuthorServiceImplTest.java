package com.example.cp2;

import com.example.cp2.domain.Author;
import com.example.cp2.dto.AuthorRecord;
import com.example.cp2.exception.ResourceNotFoundException;
import com.example.cp2.mapper.AuthorMapperImpl;
import com.example.cp2.repository.AuthorRepository;
import com.example.cp2.service.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {

    private AuthorRepository repo;
    private AuthorServiceImpl service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(AuthorRepository.class);
        service = new AuthorServiceImpl(repo, new AuthorMapperImpl());
    }

    @Test
    void findById_returnsAuthor() {
        Author a = new Author(1L, "Ana", "ana@example.com");
        when(repo.findById(1L)).thenReturn(Optional.of(a));

        var record = service.findById(1L);
        assertEquals("Ana", record.name());
    }

    @Test
    void findById_notFound_throwsException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void create_savesAuthor() {
        Author a = new Author(null, "Carlos", "carlos@example.com");
        when(repo.save(any(Author.class))).thenReturn(new Author(1L, "Carlos", "carlos@example.com"));

        var record = service.create(new AuthorRecord(null, "Carlos", "carlos@example.com"));
        assertNotNull(record.id());
        assertEquals("Carlos", record.name());
    }
}
