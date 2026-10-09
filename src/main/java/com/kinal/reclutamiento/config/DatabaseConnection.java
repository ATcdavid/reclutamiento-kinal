package com.kinal.reclutamiento.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Fábrica de conexiones JDBC; lee credenciales de db.properties. */
public class DatabaseConnection {
    private final String url;
    private final String usuario;
    private final String password;

    public DatabaseConnection() {
        Properties props = new Properties();
        try (InputStream in = getClass().getResourceAsStream("/db.properties")) {
            if (in == null) {
                throw new IllegalStateException("No se encontró db.properties");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer db.properties", e);
        }
        this.url = props.getProperty("db.url");
        this.usuario = props.getProperty("db.user");
        this.password = props.getProperty("db.password");
    }

    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }
}
