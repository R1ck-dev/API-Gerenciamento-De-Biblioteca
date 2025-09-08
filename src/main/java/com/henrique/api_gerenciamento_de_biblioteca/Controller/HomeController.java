package com.henrique.api_gerenciamento_de_biblioteca.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping(value = "/")
    public String menu() {
        return """
                Bem-Vindo
                """;
    }
}
