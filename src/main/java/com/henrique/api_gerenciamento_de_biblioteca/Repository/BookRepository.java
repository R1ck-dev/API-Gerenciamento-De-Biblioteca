package com.henrique.api_gerenciamento_de_biblioteca.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.henrique.api_gerenciamento_de_biblioteca.Model.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long>{

} 
