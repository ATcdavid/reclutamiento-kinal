package com.kinal.reclutamiento.service;

import com.kinal.reclutamiento.dto.LoginDTO;
import com.kinal.reclutamiento.dto.RegistroUsuarioDTO;
import com.kinal.reclutamiento.dto.RolDTO;
import com.kinal.reclutamiento.dto.UsuarioDTO;
import java.util.List;

public interface AuthService {
    UsuarioDTO iniciarSesion(LoginDTO login);
    void registrar(RegistroUsuarioDTO registro);
    List<RolDTO> listarRoles();
}
