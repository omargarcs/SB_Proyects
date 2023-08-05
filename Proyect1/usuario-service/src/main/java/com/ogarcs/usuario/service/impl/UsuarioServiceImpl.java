package com.ogarcs.usuario.service.impl;

import com.ogarcs.usuario.entity.Calificacion;
import com.ogarcs.usuario.entity.Usuario;
import com.ogarcs.usuario.exceptions.ResourceNotFoundException;
import com.ogarcs.usuario.repository.UsuarioRepository;
import com.ogarcs.usuario.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        String randomUsuarioId = UUID.randomUUID().toString();
        usuario.setUsuarioId(randomUsuarioId);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuario(String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).
                orElseThrow(()-> new ResourceNotFoundException
                        ("Usuario no encontrado con el ID: " + usuarioId));
        ArrayList<Calificacion> calificacionesDelUsuario = restTemplate.
                getForObject("http://localhost:8083/calificacion/usuarios/" +
                        usuario.getUsuarioId(), ArrayList.class);
        logger.info("{}", calificacionesDelUsuario);

        usuario.setCalificaciones(calificacionesDelUsuario);

        return usuario;
    }
}
