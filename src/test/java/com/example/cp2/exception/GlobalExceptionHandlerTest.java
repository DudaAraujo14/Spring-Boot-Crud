package com.example.cp2.exception;

import com.example.cp2.exception.GlobalExceptionHandler;
import com.example.cp2.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Book not found");
        WebRequest request = null;

        ResponseEntity<Object> response = handler.handleResourceNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals("Book not found", body.get("message"));
        assertEquals(404, body.get("status"));
    }

    @Test
    void shouldHandleGenericException() {
        Exception ex = new Exception("Unexpected error");
        WebRequest request = null;

        ResponseEntity<Object> response = handler.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertEquals("Unexpected error", body.get("message"));
        assertEquals(500, body.get("status"));
    }
}
