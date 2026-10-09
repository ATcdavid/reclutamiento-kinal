package com.kinal.reclutamiento.repository;

import com.kinal.reclutamiento.config.DatabaseConnection;
import com.kinal.reclutamiento.exception.PersistenciaException;
import com.kinal.reclutamiento.model.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolRepositoryJdbc implements RolRepository {
    private final DatabaseConnection database;

    public RolRepositoryJdbc(DatabaseConnection database) { this.database = database; }

    @Override
    public List<Rol> listar() {
        String sql = "SELECT id_rol, nombre FROM rol ORDER BY nombre";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Rol> roles = new ArrayList<>();
            while (rs.next()) {
                roles.add(new Rol(rs.getInt("id_rol"), rs.getString("nombre")));
            }
            return roles;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudieron cargar los roles.", e);
        }
    }

    @Override
    public Optional<Rol> buscarPorId(int id) {
        String sql = "SELECT id_rol, nombre FROM rol WHERE id_rol = ?";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(new Rol(rs.getInt("id_rol"), rs.getString("nombre")))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo consultar el rol.", e);
        }
    }
}
