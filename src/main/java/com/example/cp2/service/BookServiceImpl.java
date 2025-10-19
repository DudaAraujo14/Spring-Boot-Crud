package com.example.cp2.service;

import com.example.cp2.domain.Book;
import com.example.cp2.dto.BookRecord;
import com.example.cp2.exception.ResourceNotFoundException;
import com.example.cp2.mapper.BookMapper;
import com.example.cp2.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    public BookServiceImpl(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BookRecord create(BookRecord record) {
        Book entity = mapper.toEntity(record);
        return mapper.toRecord(repository.save(entity));
    }

    @Override
    public BookRecord findById(Long id) {
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
        return mapper.toRecord(entity);
    }

    @Override
    public List<BookRecord> findAll() {
        return repository.findAll().stream()
                .map(mapper::toRecord)
                .collect(Collectors.toList());
    }

    @Override
    public BookRecord update(Long id, BookRecord record) {
        Book existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
        existing.setTitle(record.title());
        existing.setIsbn(record.isbn());
        return mapper.toRecord(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<BookRecord> findByAuthorId(Long authorId) {
        return repository.findByAuthorId(authorId).stream()
                .map(mapper::toRecord)
                .collect(Collectors.toList());
    }
}
