package com.kinal.reclutamiento.repository;

import com.kinal.reclutamiento.model.Rol;
import java.util.List;
import java.util.Optional;

public interface RolRepository {
    List<Rol> listar();
    Optional<Rol> buscarPorId(int id);
}
