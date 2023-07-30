package com.ogarcs.calificacion.repository;

import com.ogarcs.calificacion.entity.Calificacion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CalificacionRepository extends MongoRepository<Calificacion, Long> {
}
