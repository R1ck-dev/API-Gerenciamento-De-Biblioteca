package com.henrique.api_gerenciamento_de_biblioteca.Repository;

import com.henrique.api_gerenciamento_de_biblioteca.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

}