package com.autobots.app.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashUtil {
    private HashUtil() { }

    public static String hash(String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt());
    }

    public static boolean matches(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }
}
