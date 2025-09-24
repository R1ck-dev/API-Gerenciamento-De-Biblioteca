package com.henrique.api_gerenciamento_de_biblioteca.Security;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    public static String create(String prefix, String key, JWTObject jwtObject) {
        // 1. Criar uma chave SecretKey segura a partir da sua String
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .subject(jwtObject.getSubject()) // Método atualizado
                .issuedAt(jwtObject.getIssuedAt()) // Método atualizado
                .expiration(jwtObject.getExpiration()) // Método atualizado
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles()))
                .signWith(secretKey) // Método atualizado, usando a SecretKey
                .compact();
        return prefix + " " + token;
    }

    public static JWTObject create(String token, String prefix, String key)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {

        // 2. Criar a mesma SecretKey para verificação
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

        // 3. Remover o prefixo do token
        token = token.replace(prefix + " ", ""); // Corrigido para remover o espaço também

        // 4. Construir o parser com a chave e usá-lo para validar o token
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = parser.parseSignedClaims(token).getPayload();

        JWTObject object = new JWTObject();
        object.setSubject(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssuedAt(claims.getIssuedAt());
        object.setRoles(((List<?>) claims.get(ROLES_AUTHORITIES)).stream()
                .map(Object::toString)
                .collect(Collectors.toList()));

        return object;
    }

    private static List<String> checkRoles(List<String> roles) {
        return roles.stream().map(s -> "ROLE_".concat(s.replaceAll("ROLE_", ""))).collect(Collectors.toList());
    }
}