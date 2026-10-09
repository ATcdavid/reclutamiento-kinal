package com.kinal.reclutamiento.repository;

import com.kinal.reclutamiento.config.DatabaseConnection;
import com.kinal.reclutamiento.exception.PersistenciaException;
import com.kinal.reclutamiento.model.Aspirante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AspiranteRepositoryJdbc implements AspiranteRepository {
    private static final String COLUMNAS =
            "id_aspirante, nombre, apellido, correo, telefono, direccion, edad";

    private final DatabaseConnection database;

    public AspiranteRepositoryJdbc(DatabaseConnection database) { this.database = database; }

    @Override
    public List<Aspirante> listar() {
        String sql = "SELECT " + COLUMNAS + " FROM aspirante ORDER BY id_aspirante";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Aspirante> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo obtener la lista de aspirantes.", e);
        }
    }

    @Override
    public Optional<Aspirante> buscarPorCorreo(String correo) {
        String sql = "SELECT " + COLUMNAS + " FROM aspirante WHERE correo = ?";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo consultar el aspirante.", e);
        }
    }

    @Override
    public void guardar(Aspirante a) {
        String sql = "INSERT INTO aspirante (nombre, apellido, correo, telefono, direccion, edad) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getCorreo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getDireccion());
            ps.setInt(6, a.getEdad());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo guardar el aspirante.", e);
        }
    }

    @Override
    public boolean actualizar(Aspirante a) {
        String sql = "UPDATE aspirante SET nombre = ?, apellido = ?, correo = ?, telefono = ?, "
                   + "direccion = ?, edad = ? WHERE id_aspirante = ?";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getCorreo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getDireccion());
            ps.setInt(6, a.getEdad());
            ps.setInt(7, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo actualizar el aspirante.", e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM aspirante WHERE id_aspirante = ?";
        try (Connection con = database.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo eliminar el aspirante.", e);
        }
    }

    private Aspirante mapear(ResultSet rs) throws SQLException {
        return new Aspirante(rs.getInt("id_aspirante"), rs.getString("nombre"),
                rs.getString("apellido"), rs.getString("correo"), rs.getString("telefono"),
                rs.getString("direccion"), rs.getInt("edad"));
    }
}
