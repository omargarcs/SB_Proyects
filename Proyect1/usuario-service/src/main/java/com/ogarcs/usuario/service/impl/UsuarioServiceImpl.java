package com.ogarcs.usuario.service.impl;

import com.ogarcs.usuario.entity.Calificacion;
import com.ogarcs.usuario.entity.Hotel;
import com.ogarcs.usuario.entity.Usuario;
import com.ogarcs.usuario.exceptions.ResourceNotFoundException;
import com.ogarcs.usuario.external.services.HotelService;
import com.ogarcs.usuario.repository.UsuarioRepository;
import com.ogarcs.usuario.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService {

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HotelService hotelService;

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

        Calificacion[] calificacionesDelUsuario = restTemplate.
                getForObject("http://CALIFICACION-SERVICE/calificacion/usuarios/" +
                        usuario.getUsuarioId(), Calificacion[].class);

        List<Calificacion> calificaciones = Arrays.stream(calificacionesDelUsuario).collect(Collectors.toList());

        List<Calificacion> listaCalificaciones = calificaciones.stream().map(calificacion -> {
            System.out.println(calificacion.getHotelId());
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hoteles/" +
            //        calificacion.getHotelId(), Hotel.class);
            Hotel hotel = hotelService.getHotel(calificacion.getHotelId());
            //logger.info("Respuesta con código: {}", forEntity.getStatusCode());
            calificacion.setHotel(hotel);
            return calificacion;
        }).collect(Collectors.toList());

        usuario.setCalificaciones(listaCalificaciones);

        return usuario;
    }
}
