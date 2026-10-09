package com.kinal.reclutamiento.service;

import com.kinal.reclutamiento.dto.LoginDTO;
import com.kinal.reclutamiento.dto.RegistroUsuarioDTO;
import com.kinal.reclutamiento.dto.RolDTO;
import com.kinal.reclutamiento.dto.UsuarioDTO;
import com.kinal.reclutamiento.exception.AuthException;
import com.kinal.reclutamiento.exception.ValidacionException;
import com.kinal.reclutamiento.model.Rol;
import com.kinal.reclutamiento.model.Usuario;
import com.kinal.reclutamiento.repository.RolRepository;
import com.kinal.reclutamiento.repository.UsuarioRepository;
import com.kinal.reclutamiento.util.PasswordHasher;
import com.kinal.reclutamiento.util.Validador;
import java.util.List;
import java.util.stream.Collectors;

public class AuthServiceImpl implements AuthService {
    private static final String MENSAJE_CREDENCIALES = "Correo o contraseña incorrectos.";

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public UsuarioDTO iniciarSesion(LoginDTO login) {
        Validador.requerido(login.getCorreo(), "Correo");
        Validador.requerido(login.getPassword(), "Contraseña");

        Usuario usuario = usuarioRepository.buscarPorCorreo(login.getCorreo().trim())
                .orElseThrow(() -> new AuthException(MENSAJE_CREDENCIALES));

        if (!PasswordHasher.verificar(login.getPassword(), usuario.getPasswordHash())) {
            throw new AuthException(MENSAJE_CREDENCIALES);
        }
        return new UsuarioDTO(usuario.getId(),
                usuario.getNombre() + " " + usuario.getApellido(),
                usuario.getCorreo(), usuario.getRol().getNombre());
    }

    @Override
    public void registrar(RegistroUsuarioDTO registro) {
        Validador.requerido(registro.getNombre(), "Nombre");
        Validador.requerido(registro.getApellido(), "Apellido");
        Validador.correo(registro.getCorreo());
        Validador.password(registro.getPassword());
        if (registro.getRol() == null) {
            throw new ValidacionException("Debe seleccionar un rol.");
        }
        String correo = registro.getCorreo().trim();
        if (usuarioRepository.buscarPorCorreo(correo).isPresent()) {
            throw new AuthException("Ya existe un usuario registrado con ese correo.");
        }
        Rol rol = rolRepository.buscarPorId(registro.getRol().getId())
                .orElseThrow(() -> new ValidacionException("El rol seleccionado no existe."));

        Usuario nuevo = new Usuario(0, registro.getNombre().trim(), registro.getApellido().trim(),
                correo, PasswordHasher.hash(registro.getPassword()), rol);
        usuarioRepository.guardar(nuevo);
    }

    @Override
    public List<RolDTO> listarRoles() {
        return rolRepository.listar().stream()
                .map(r -> new RolDTO(r.getId(), r.getNombre()))
                .collect(Collectors.toList());
    }
}
