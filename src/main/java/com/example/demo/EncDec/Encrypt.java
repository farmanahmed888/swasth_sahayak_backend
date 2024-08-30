package com.example.demo.EncDec;

import com.example.demo.Entity.fieldworker.fieldworkerDetails;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

@Converter
public class Encrypt  implements AttributeConverter<String, String> {

    private static final String ENCRYPTION_PASSWORD_PROPERTY = "jasypt.encryptor.password";

    private static final Logger logger = LoggerFactory.getLogger(Encrypt.class);
    private final StandardPBEStringEncryptor encryptor;

    public Encrypt(Environment environment) {

        this.encryptor = new StandardPBEStringEncryptor();
        this.encryptor.setPassword(environment.getProperty(ENCRYPTION_PASSWORD_PROPERTY));
    }





    @Override
    public String convertToDatabaseColumn(String s) {
        logger.debug("Encrypting string: {}", s);
        String encryptedValue = encryptor.encrypt(s);
        logger.debug("Encrypted value: {}", encryptedValue);
        return encryptedValue;
    }

    @Override
    public String convertToEntityAttribute(String s) {
        logger.debug("Decrypting string: {}", s);
        String decryptedValue = encryptor.decrypt(s);
        logger.debug("Decrypted value: {}", decryptedValue);
        return decryptedValue;
    }


}
