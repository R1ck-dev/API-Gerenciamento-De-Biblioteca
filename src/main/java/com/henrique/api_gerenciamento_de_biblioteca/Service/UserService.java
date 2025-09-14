package com.henrique.api_gerenciamento_de_biblioteca.Service;

import org.springframework.security.core.userdetails.UserDetails;

import com.henrique.api_gerenciamento_de_biblioteca.DTO.UserDTO;
import com.henrique.api_gerenciamento_de_biblioteca.Model.UserModel;

public interface UserService {

    UserModel createUser(UserDTO dto);

    UserDetails loadUserByUsername(String email);
    
} 