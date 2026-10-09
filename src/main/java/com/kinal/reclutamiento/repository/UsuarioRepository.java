package com.kinal.reclutamiento.repository;

import com.kinal.reclutamiento.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> buscarPorCorreo(String correo);
    void guardar(Usuario usuario);
}
