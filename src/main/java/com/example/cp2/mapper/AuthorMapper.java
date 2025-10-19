package com.example.cp2.mapper;

import com.example.cp2.domain.Author;
import com.example.cp2.dto.AuthorRecord;

public interface AuthorMapper {
    AuthorRecord toRecord(Author entity);
    Author toEntity(AuthorRecord record);
}
