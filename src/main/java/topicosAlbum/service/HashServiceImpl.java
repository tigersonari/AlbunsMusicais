package topicosAlbum.service;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashServiceImpl implements HashService {

    
    private static final String SALT = "#$127732&";
    private static final int ITERATION_COUNT = 403;
    private static final int KEY_LENGTH = 512;

    @Override
    public String getHashSenha(String senha) {
        if (senha == null) {
            throw new IllegalArgumentException("Senha não pode ser nula");
        }

        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(
                            senha.toCharArray(),
                            SALT.getBytes(),
                            ITERATION_COUNT,
                            KEY_LENGTH))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(result);

        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            // aqui pode logar se quiser
            throw new RuntimeException("Erro ao gerar o hash da senha", e);
        }
    }
}
