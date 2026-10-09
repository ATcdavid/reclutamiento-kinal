package com.kinal.reclutamiento.service;

import com.kinal.reclutamiento.dto.AspiranteDTO;
import java.util.List;

public interface AspiranteService {
    List<AspiranteDTO> listar();
    void agregar(AspiranteDTO aspirante);
    void actualizar(AspiranteDTO aspirante);
    void eliminar(int id);
}
