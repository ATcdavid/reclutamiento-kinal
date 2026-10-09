package com.kinal.reclutamiento.util;

import com.kinal.reclutamiento.exception.ValidacionException;
import java.util.regex.Pattern;

public final class Validador {
    private static final Pattern CORREO = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern TELEFONO = Pattern.compile("^[0-9+\\- ]{8,15}$");

    private Validador() { }

    public static void requerido(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo '" + campo + "' es obligatorio.");
        }
    }

    public static void correo(String valor) {
        requerido(valor, "Correo");
        if (!CORREO.matcher(valor.trim()).matches()) {
            throw new ValidacionException("El correo electrónico no tiene un formato válido.");
        }
    }

    public static void telefono(String valor) {
        requerido(valor, "Teléfono");
        if (!TELEFONO.matcher(valor.trim()).matches()) {
            throw new ValidacionException("El teléfono debe tener entre 8 y 15 dígitos.");
        }
    }

    public static void edad(int edad) {
        if (edad < 18 || edad > 100) {
            throw new ValidacionException("La edad debe estar entre 18 y 100 años.");
        }
    }

    public static void password(String valor) {
        requerido(valor, "Contraseña");
        if (valor.length() < 6) {
            throw new ValidacionException("La contraseña debe tener al menos 6 caracteres.");
        }
    }
}
