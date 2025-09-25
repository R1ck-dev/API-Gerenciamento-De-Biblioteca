package com.henrique.api_gerenciamento_de_biblioteca.Controller;

import com.henrique.api_gerenciamento_de_biblioteca.DTO.LoginDTO;
import com.henrique.api_gerenciamento_de_biblioteca.DTO.SectionDTO;
import com.henrique.api_gerenciamento_de_biblioteca.Model.UserModel;
import com.henrique.api_gerenciamento_de_biblioteca.Repository.UserRepository;
import com.henrique.api_gerenciamento_de_biblioteca.Security.JWTCreator;
import com.henrique.api_gerenciamento_de_biblioteca.Security.JWTObject;
import com.henrique.api_gerenciamento_de_biblioteca.Security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public SectionDTO logar(@RequestBody LoginDTO login) {
        UserModel user = repository.findByEmail(login.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + login.getUsername()));

        boolean passwordOk = encoder.matches(login.getPassword(), user.getPassword());
        if (!passwordOk) {
            throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
        }

        SectionDTO sessao = new SectionDTO();
        sessao.setLogin(user.getUsername());

        JWTObject jwtObject = new JWTObject();
        jwtObject.setSubject(user.getUsername());
        jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtObject.setExpiration(new Date(System.currentTimeMillis() + WebSecurityConfig.EXPIRATION));
        
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        jwtObject.setRoles(roles);

        sessao.setToken(JWTCreator.create(WebSecurityConfig.PREFIX, WebSecurityConfig.KEY, jwtObject));

        return sessao;
    }
}