package com.example.cp2.service;

import com.example.cp2.dto.AuthorRecord;
import java.util.List;

public interface AuthorService {
    AuthorRecord create(AuthorRecord record);
    AuthorRecord findById(Long id);
    List<AuthorRecord> findAll();
    AuthorRecord update(Long id, AuthorRecord record);
    void delete(Long id);
}
