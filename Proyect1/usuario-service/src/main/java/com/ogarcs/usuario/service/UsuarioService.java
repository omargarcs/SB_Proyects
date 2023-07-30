package com.ogarcs.usuario.service;

import com.ogarcs.usuario.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario saveUsuario(Usuario usuario);

    List<Usuario> getAllUsuarios();

    Usuario getUsuario(String usuarioId);
}
