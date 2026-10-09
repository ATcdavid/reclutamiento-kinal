package com.kinal.reclutamiento.service;

import com.kinal.reclutamiento.dto.AspiranteDTO;
import com.kinal.reclutamiento.model.Aspirante;

final class AspiranteMapper {
    private AspiranteMapper() { }

    static AspiranteDTO aDto(Aspirante a) {
        return new AspiranteDTO(a.getId(), a.getNombre(), a.getApellido(), a.getCorreo(),
                a.getTelefono(), a.getDireccion(), a.getEdad());
    }

    static Aspirante aEntidad(AspiranteDTO d) {
        return new Aspirante(d.getId(), d.getNombre().trim(), d.getApellido().trim(),
                d.getCorreo().trim(), d.getTelefono().trim(), d.getDireccion().trim(), d.getEdad());
    }
}
