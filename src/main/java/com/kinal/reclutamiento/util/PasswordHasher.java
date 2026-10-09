package com.kinal.reclutamiento.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordHasher {
    private PasswordHasher() { }

    public static String hash(String passwordPlano) {
        return BCrypt.hashpw(passwordPlano, BCrypt.gensalt(12));
    }

    public static boolean verificar(String passwordPlano, String hash) {
        return BCrypt.checkpw(passwordPlano, hash);
    }
}
