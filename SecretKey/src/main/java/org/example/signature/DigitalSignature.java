package org.example.signature;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.example.ReadKey;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class DigitalSignature {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            // Tạo cặp khóa
            KeyPair keyPair = generateKeyPair();
            RSAPrivateKey rsaPrivateKey = ReadKey.readPKCS8PrivateKey(new File("private_key.pem"));
            RSAPublicKey rsaPublicKey = ReadKey.readX509PublicKey(new File("public_key.pem"));
            // Dữ liệu cần ký
            String dataToSign = "Hello, World!";

            // Tạo chữ ký điện tử bằng private key
            byte[] signature = sign(dataToSign.getBytes(), rsaPrivateKey);

            // Xác nhận chữ ký bằng public key
            boolean verified = verify(dataToSign.getBytes(), signature, rsaPublicKey);

            // Hiển thị kết quả
            System.out.println("Chữ ký điện tử đã được xác nhận: " + verified);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    private static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withRSA", "BC");
        verifier.initVerify(publicKey);
        verifier.update(data);
        return verifier.verify(signature);
    }
}
