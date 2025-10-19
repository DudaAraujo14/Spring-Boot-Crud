package com.example.cp2.web;

import com.example.cp2.dto.BookRecord;
import com.example.cp2.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookRecord> create(@RequestBody BookRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(record));
    }

    @GetMapping
    public List<BookRecord> listAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookRecord> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookRecord> update(@PathVariable Long id, @RequestBody BookRecord record) {
        return ResponseEntity.ok(bookService.update(id, record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
