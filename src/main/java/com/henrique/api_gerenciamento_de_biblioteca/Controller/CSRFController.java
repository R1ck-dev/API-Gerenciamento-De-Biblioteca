package com.henrique.api_gerenciamento_de_biblioteca.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CSRFController {
    @GetMapping(value = "/csrf")
    public ResponseEntity<Void> getCSRFToken() {
        return ResponseEntity.ok().build();
    }
}
