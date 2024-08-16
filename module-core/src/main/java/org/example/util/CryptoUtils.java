package org.example.util;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtils {

  @Value("${crypto.private-key}")
  private String nonStaticPrivateKey;

  @Value("${crypto.transformation}")
  private String nonStaticTransformation;

  private static String privateKey;
  private static String transformation;

  @PostConstruct
  public void init() {
    privateKey = nonStaticPrivateKey;
    transformation = nonStaticTransformation;
  }

  public static String encode(String plainText) {
    try {
      SecretKeySpec secretKey = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(privateKey.substring(0, 16).getBytes());

      Cipher c = Cipher.getInstance(transformation);
      c.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
      byte[] encryptByte = c.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
      return Hex.encodeHexString(encryptByte);
    } catch (Exception e) {
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "암호화 과정에서 오류가 발생했습니다.");
    }
  }


  public static String decode(String encodeText) {
    try {
      SecretKeySpec secretKey = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(privateKey.substring(0, 16).getBytes());

      Cipher c = Cipher.getInstance(transformation);
      c.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
      byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());
      return new String(c.doFinal(decodeByte), StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "복호화 과정에서 오류가 발생했습니다.");
    }
    }

  }
