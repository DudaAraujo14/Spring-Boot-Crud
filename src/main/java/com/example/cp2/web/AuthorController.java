package com.example.cp2.web;

import com.example.cp2.dto.AuthorRecord;
import com.example.cp2.dto.BookRecord;
import com.example.cp2.service.AuthorService;
import com.example.cp2.service.BookService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<AuthorRecord> create(@RequestBody AuthorRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(record));
    }

    @GetMapping
    public List<AuthorRecord> listAll() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorRecord> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorRecord> update(@PathVariable("id") Long id, @RequestBody AuthorRecord record) {
        return ResponseEntity.ok(authorService.update(id, record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/books")
    public List<BookRecord> listBooksByAuthor(@PathVariable("id") Long id) {
        return bookService.findByAuthorId(id);
    }
}
