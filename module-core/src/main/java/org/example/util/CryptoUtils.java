package org.example.util;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;

public class CryptoUtils {

  private static final String PRIVATE_KEY = "PROJECT_X_KOALA_FIGHTING";
  private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

  public static String encode(String plainText) {
    try {
      SecretKeySpec secretKey = new SecretKeySpec(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), "AES");
      IvParameterSpec IV = new IvParameterSpec(PRIVATE_KEY.substring(0, 16).getBytes());

      Cipher c = Cipher.getInstance(TRANSFORMATION);
      c.init(Cipher.ENCRYPT_MODE, secretKey, IV);
      byte[] encryptByte = c.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
      return Hex.encodeHexString(encryptByte);
    } catch (Exception e) {
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "암호화 과정에서 오류가 발생했습니다.");
    }
  }


  public static String decode(String encodeText) {
    try {
      SecretKeySpec secretKey = new SecretKeySpec(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), "AES");
      IvParameterSpec IV = new IvParameterSpec(PRIVATE_KEY.substring(0, 16).getBytes());

      Cipher c = Cipher.getInstance(TRANSFORMATION);
      c.init(Cipher.DECRYPT_MODE, secretKey, IV);
      byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());
      return new String(c.doFinal(decodeByte), StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "복호화 과정에서 오류가 발생했습니다.");
    }
    }

  }
