package Usuarios.Repository;
import Usuarios.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);
   @Query("SELECT u FROM UserModel u WHERE u.nome = :nome")
    List<UserModel> usuarios(@Param("nome") String nome);
}
