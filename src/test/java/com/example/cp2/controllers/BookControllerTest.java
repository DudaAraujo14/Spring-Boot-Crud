package com.example.cp2.controllers;

import com.example.cp2.Cp2Application;
import com.example.cp2.controllers.BookController;
import com.example.cp2.dto.BookRecord;
import com.example.cp2.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = Cp2Application.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @Test
    void shouldReturnListOfBooks() throws Exception {
        BookRecord record = new BookRecord(1L, "Clean Code", "12345", 1L);
        when(service.findAll()).thenReturn(List.of(record));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }

    @Test
    void shouldFindBookById() throws Exception {
        BookRecord record = new BookRecord(1L, "Clean Code", "12345", 1L);
        when(service.findById(1L)).thenReturn(record);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void shouldCreateBook() throws Exception {
        BookRecord record = new BookRecord(1L, "Clean Code", "12345", 1L);
        when(service.create(any())).thenReturn(record);

        String json = """
            {"id":1,"title":"Clean Code","isbn":"12345","authorId":1}
            """;

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        BookRecord record = new BookRecord(1L, "Clean Code", "12345", 1L);
        when(service.update(any(Long.class), any(BookRecord.class))).thenReturn(record);

        String json = """
            {"id":1,"title":"Clean Code","isbn":"12345","authorId":1}
            """;

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }
}
