package topicosAlbum.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

    private static final String SALT = "#$127732&"; // igual ao import.sql
    private static final int ITERATIONS = 403;       // igual ao import.sql
    private static final int KEY_LENGTH = 512;       // igual ao import.sql

    public static String hash(String password) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    SALT.getBytes(),
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hashed = skf.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hashed);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao gerar hash da senha.", e);
        }
    }
}
