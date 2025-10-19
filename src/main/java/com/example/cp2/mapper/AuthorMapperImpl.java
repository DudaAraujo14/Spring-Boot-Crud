package com.example.cp2.mapper;

import com.example.cp2.domain.Author;
import com.example.cp2.dto.AuthorRecord;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements AuthorMapper {
    @Override
    public AuthorRecord toRecord(Author e) {
        if (e == null) return null;
        return new AuthorRecord(e.getId(), e.getName(), e.getEmail());
    }
    @Override
    public Author toEntity(AuthorRecord r) {
        if (r == null) return null;
        Author a = new Author();
        a.setId(r.id());
        a.setName(r.name());
        a.setEmail(r.email());
        return a;
    }
}
