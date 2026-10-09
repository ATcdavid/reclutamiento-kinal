package com.kinal.reclutamiento.view;

import com.kinal.reclutamiento.dto.UsuarioDTO;

/** Guarda el usuario autenticado mientras la aplicación está abierta. */
public class SesionUsuario {
    private UsuarioDTO usuario;

    public void iniciar(UsuarioDTO usuario) { this.usuario = usuario; }
    public void cerrar() { this.usuario = null; }
    public UsuarioDTO getUsuario() { return usuario; }
}
