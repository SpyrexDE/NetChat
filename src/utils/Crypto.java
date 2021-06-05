package utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    
    public static KeyPair generateKeyPair() {
        
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        keyPairGen.initialize(4096);
        KeyPair keyPair = keyPairGen.genKeyPair();
        return keyPair;
    }

    public static String encrypt (String plainText, PublicKey pubKey) throws Exception {

        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();

        //AES encrypt message
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        String cipherB64 = Base64.getEncoder().encodeToString(byteCipherText);

        //RSA encrypt AES key
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.PUBLIC_KEY, pubKey);
        byte[] encryptedKey = cipher.doFinal(secKey.getEncoded());
        String encKeyB64 = Base64.getEncoder().encodeToString(encryptedKey);

        return new String(cipherB64 + ";" + encKeyB64);
    }
    
    public static String decrypt (String cipherText, PrivateKey privKey) throws Exception {
        
        String[] cipherSplit = cipherText.split(";", -1);
        
        String cipherB64 = cipherSplit[0];
        String encKeyB64 = cipherSplit[1];

        byte[] byteCipherText = Base64.getDecoder().decode(cipherB64);
        byte[] encryptedKey = Base64.getDecoder().decode(encKeyB64);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.PRIVATE_KEY, privKey);
        byte[] decryptedKey = cipher.doFinal(encryptedKey);

        //Convert bytes to AES SecertKey
        SecretKey originalKey = new SecretKeySpec(decryptedKey , 0, decryptedKey .length, "AES");
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        String plainText = new String(bytePlainText);

        return plainText;
    }

    public static PublicKey parsePubKeyB64(String pubKeyB64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64.getDecoder().decode(pubKeyB64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
