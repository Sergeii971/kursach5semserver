package com.verbovskiy.server.util.encryption;

import com.verbovskiy.server.exception.EncryptionException;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Cryptographer.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class Cryptographer {
    private final String ALGORITHM = "SHA-512";

    /**
     * Encrypt password string.
     *
     * @param line the encrypted line
     * @return the string
     */
    public String encrypt(String line) throws EncryptionException {
        try {
            MessageDigest digester = MessageDigest.getInstance(ALGORITHM);
            byte[] input = line.getBytes(StandardCharsets.UTF_8);
            byte[] digest = digester.digest(input);
            String encryptedPassword = DatatypeConverter.printHexBinary(digest);
            return encryptedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e);
        }
    }
}
