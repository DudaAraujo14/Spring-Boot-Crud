package com.example.cp2.controllers;

import com.example.cp2.dto.AuthorRecord;
import com.example.cp2.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void shouldReturnAuthorsList() throws Exception {
        AuthorRecord author = new AuthorRecord(1L, "Robert Martin", "clean@code.com");
        when(authorService.findAll()).thenReturn(List.of(author));

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Robert Martin"));
    }

    @Test
    void shouldFindAuthorById() throws Exception {
        AuthorRecord author = new AuthorRecord(1L, "Robert Martin", "clean@code.com");
        when(authorService.findById(1L)).thenReturn(author);

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Robert Martin"));
    }

    @Test
    void shouldCreateAuthor() throws Exception {
        AuthorRecord author = new AuthorRecord(1L, "Robert Martin", "clean@code.com");
        when(authorService.create(any())).thenReturn(author);

        String json = """
            {"id":1,"name":"Robert Martin","email":"clean@code.com"}
            """;

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Robert Martin"));
    }

    @Test
    void shouldUpdateAuthor() throws Exception {
        AuthorRecord author = new AuthorRecord(1L, "Robert Martin", "clean@code.com");
        when(authorService.update(any(Long.class), any(AuthorRecord.class))).thenReturn(author);

        String json = """
            {"id":1,"name":"Robert Martin","email":"clean@code.com"}
            """;

        mockMvc.perform(put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Robert Martin"));
    }

    @Test
    void shouldDeleteAuthor() throws Exception {
        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isNoContent());
    }
}
