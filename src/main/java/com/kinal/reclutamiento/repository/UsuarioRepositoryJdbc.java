package com.kinal.reclutamiento.repository;

import com.kinal.reclutamiento.config.DatabaseConnection;
import com.kinal.reclutamiento.exception.PersistenciaException;
import com.kinal.reclutamiento.model.Rol;
import com.kinal.reclutamiento.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioRepositoryJdbc implements UsuarioRepository {
    private final DatabaseConnection database;

    public UsuarioRepositoryJdbc(DatabaseConnection database) { this.database = database; }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        String sql = "SELECT u.id_usuario, u.nombre, u.apellido, u.correo, u.password_hash, "
                   + "r.id_rol, r.nombre AS rol "
                   + "FROM usuario u JOIN rol r ON r.id_rol = u.id_rol WHERE u.correo = ?";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("rol"));
                return Optional.of(new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"),
                        rs.getString("apellido"), rs.getString("correo"),
                        rs.getString("password_hash"), rol));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo consultar el usuario.", e);
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, apellido, correo, password_hash, id_rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getPasswordHash());
            ps.setInt(5, usuario.getRol().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo registrar el usuario.", e);
        }
    }
}
