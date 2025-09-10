package com.henrique.api_gerenciamento_de_biblioteca.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.henrique.api_gerenciamento_de_biblioteca.Model.AuthorModel;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorModel, Long>{

} 
