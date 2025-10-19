package com.example.cp2.service;

import com.example.cp2.domain.Author;
import com.example.cp2.dto.AuthorRecord;
import com.example.cp2.exception.ResourceNotFoundException;
import com.example.cp2.mapper.AuthorMapper;
import com.example.cp2.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public AuthorServiceImpl(AuthorRepository repository, AuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AuthorRecord create(AuthorRecord record) {
        Author entity = mapper.toEntity(record);
        return mapper.toRecord(repository.save(entity));
    }

    @Override
    public AuthorRecord findById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
        return mapper.toRecord(author);
    }

    @Override
    public List<AuthorRecord> findAll() {
        return repository.findAll().stream().map(mapper::toRecord).toList();
    }

    @Override
    public AuthorRecord update(Long id, AuthorRecord record) {
        Author existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));
        existing.setName(record.name());
        existing.setEmail(record.email());
        return mapper.toRecord(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id " + id);
        }
        repository.deleteById(id);
    }
}
