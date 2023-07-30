package com.ogarcs.usuario.repository;

import com.ogarcs.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, String> {
}
