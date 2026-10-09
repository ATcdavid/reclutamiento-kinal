package com.kinal.reclutamiento.repository;

import com.kinal.reclutamiento.model.Aspirante;
import java.util.List;
import java.util.Optional;

public interface AspiranteRepository {
    List<Aspirante> listar();
    Optional<Aspirante> buscarPorCorreo(String correo);
    void guardar(Aspirante aspirante);
    boolean actualizar(Aspirante aspirante);
    boolean eliminar(int id);
}
