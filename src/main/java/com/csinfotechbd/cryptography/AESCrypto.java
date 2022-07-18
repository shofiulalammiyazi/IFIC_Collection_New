package com.csinfotechbd.cryptography;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

/**
 * Created by Tanmoy on 5/9/2017.
 * Updated by Tanmoy on 5/9/2017.
 * Modified by Yasir Araphat on 28 April 2021
 */
@Service
public class AESCrypto {

    private static final String ALGORITHM = "AES";

    private static final String TRANSFORMATION = "AES";

    public static final String ENCRYPTION_KEY = "YeH_128-b1t$,Br0";

    public static byte[] encrypt(byte[] inputBytes)
            throws CryptoException {
        return doCrypto(Cipher.ENCRYPT_MODE, ENCRYPTION_KEY, inputBytes);
    }

    public static byte[] encrypt(String key, byte[] inputBytes)
            throws CryptoException {
        return doCrypto(Cipher.ENCRYPT_MODE, key, inputBytes);
    }

    public static byte[] decrypt(byte[] encryptedBytes)
            throws CryptoException {
        return doCrypto(Cipher.DECRYPT_MODE, ENCRYPTION_KEY, encryptedBytes);
    }

    public static byte[] decrypt(String key, byte[] encryptedBytes)
            throws CryptoException {
        return doCrypto(Cipher.DECRYPT_MODE, key, encryptedBytes);
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] inputBytes) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
            return cipher.doFinal(inputBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException ex) {
            String errorInfo = "Encrypting/decrypting error = " + ex.getMessage() +
                    ",\n Generated from " + ex.getStackTrace()[6].toString();
            throw new CryptoException(errorInfo, ex);
        }
    }
}
