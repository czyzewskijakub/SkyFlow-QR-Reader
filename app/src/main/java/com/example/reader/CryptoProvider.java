package com.example.reader;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CryptoProvider {

    public final static String PRIVATE_KEY =
                    "MIIEogIBAAKCAQBut7NUjvC9kGJmD3qoaHCNxGMscHziNXbUGaB2OqRZ8OWgu6BI" +
                    "yKw4ySrvxSXeJB8MseV5Y4irVrTov6rrhmpTGhLZpNJUQi4qbjBYca9XABU3MQ7e" +
                    "7jn+8psGVJZmDEv25KHfvw6QfeIotBnYADFgfCCmKsZBjdNquv6zFjHSZisAaqvP" +
                    "wuhRPpWFtvcL5ZS9ow1qMzQULIZPS9fYbO5SHyjs49B75GbA/yP4fHVeUdzfofMg" +
                    "8byckDUG5YyXb3ah4tuCS6c5Z83Ei3q6jXULgOxMXzDtX6re2eJz9qrbq9Bdg7cQ" +
                    "1fT5vfmzkfkzzE+jOYAJCfLhLM6gXqsuGf/NAgMBAAECggEAOzFfYQ/iyZZN5T7V" +
                    "VwAvXLvoQbQKMG5hD1h8H3mOsL9IJTzqnr8axGlY2UPP0QoCx6i8hL+gK+p5Yg5G" +
                    "9RCx+6fu+AlUodOYFvs+QarWEW9r6EwEJU+8Ff/ivemvVvOwEVl+6b6drMaAiT5w" +
                    "qri/rVPvqKcpw5Ztt5wt29U74b4f2KTKcLP38+uZrt70HwZMH+tB1//zzvHMK1nG" +
                    "thIGiqnvVxQjcfIcCQKNOC9IMQkhdJtHKva+oG8hfb0wFQ4BKTTq+vg/pd6+e6UX" +
                    "4zfvjd8XHj/5rUx+6uxi2KggtBoWUNt6k6J5wWjuKJw1eeYLFTZtz0p/vN36fzkZ" +
                    "FZDeAQKBgQDZBw21P9zF/ogfIIf7HB5He+63PLdkYWRAtPRfuVKNpLIb2UXX0Tjv" +
                    "u+vwExRuWPYq4MziqHLjPtBpx/450UCJNeoMRDCreIgv2zIHqo9QchJ+vuZjHUov" +
                    "vCdpLafKHlT0QSDAL2+PSrMhEw0DsOqEysZYbUwdtoH8l7s4gQN0DQKBgQCCmXwJ" +
                    "VBnMwHyVnoEooxaGAsWjyorg4Fj87O7e+0CwDHLs5F0HVlQYfyUwdO2+3G7VmAqI" +
                    "rExWAQTLsT459cL7Vr0z9EXle5eAJIE2zCHz+/mInsPtrIAucAwcGK4dEuQS7kyy" +
                    "4SEX9aka3qoeVp+l6Pc7Q7Zbqql7EJN9FxMKwQKBgQCLqeVhccKizCVPWsEHkVXP" +
                    "DOtXkkQ23hIFLi5hd8KvkoBoOY+fzm3EoXu5WJ36UVfByj8v0dV1dlPq7yAklwvg" +
                    "AQkoca2ce4mwum/czR9DVtWeKT3cJNo8E+qM3iuvf4uOUGdCJoS1gw35x+fFK1IQ" +
                    "uOmhuFyWdn/6TK7YMvZE9QKBgHGQPYdvFjAxgSQpfG51wnQtnObcjP8Wlsr4zI4h" +
                    "NRLhAF52BV8H9oCGrzP/uWyqCca6Ow61pHt0z1LAgTetOSuNNWEkHFN8sTTAiYu4" +
                    "3h1nEol2ZTEbOysmS6Fy5JRrhN/hT9iMY1aOS8oNrShMcu62MaXbL4tuT4mv1FCF" +
                    "7A0BAoGAUZjH/ahwwcoEWObSZSUzEWjnkI/1hAGUq23V921XS1eUWbp/MemeUlu5" +
                    "0SZHqz6e2QBvw2uk7Ny0aEtzZ61yc6zpnrrUallYOEOI29M3zkS36WJVaiKs/qnY" +
                    "InybHF1i4nja3KEm6socbteiEYaLxRpsYxJ7UbH9QoIfVkZNP30=";

    public static PrivateKey readPrivateKey() throws Exception {

        Security.addProvider(
                new BouncyCastleProvider()
        );
        byte[] encoded = Base64.getDecoder().decode(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }

    public static String decode(PrivateKey privateKey, String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher decryptionCipher = Cipher.getInstance("RSA");
        decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage =
                decryptionCipher.doFinal( Base64.getDecoder().decode(message));
        String decryption = new String(decryptedMessage);
        return decryption;
    }


}
