package com.jdeleonc.foro.forohub.repository;

import com.jdeleonc.foro.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    UserDetails findByUsername(String username);
}
