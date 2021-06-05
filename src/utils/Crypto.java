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
        
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] cipherBytes = cipher.doFinal(plainText.getBytes()) ;

        return new String(cipherBytes);
    }
    
    public static String decrypt (byte[] cipherBytes, PrivateKey privKey) throws Exception {
        
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        
        byte[] decryptedBytes = cipher.doFinal(cipherBytes);
        
        return new String(decryptedBytes);
    }

    public static PublicKey parsePubKeyB64(String pubKeyB64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64.getDecoder().decode(pubKeyB64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
