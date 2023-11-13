package org.example;
import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
public class Main {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        RSAPrivateKey rsaPrivateKey = ReadKey.readPKCS8PrivateKey(new File("private_key.pem"));
        RSAPublicKey rsaPublicKey = ReadKey.readX509PublicKey(new File("public_key.pem"));
        // Original message
        String originalMessage = "Hello,  dgsdgwrhrv  ửgergervgerg!";

        // Encrypt with PublicKey
        byte[] encryptedMessage = encryptWithPublicKey(originalMessage, rsaPublicKey);
        System.out.println("Encrypted with PublicKey: " + new String(encryptedMessage));

        // Decrypt with PrivateKey
        String decryptedMessage = decryptWithPrivateKey(encryptedMessage, rsaPrivateKey);
        System.out.println("Decrypted with PrivateKey: " + decryptedMessage);



    }

    private static byte[] encryptWithPublicKey(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    private static String decryptWithPrivateKey(byte[] encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedMessage);
        return new String(decryptedBytes);
    }
    private static String decryptWithPublicKey(byte[] encryptedMessage, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
        signature.initVerify(publicKey);

        // Thực hiện giải mã
        boolean isVerified = signature.verify(encryptedMessage);

        if (isVerified) {
            return "Signature verified!";
        } else {
            return "Signature verification failed!";
        }
    }
}