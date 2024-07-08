package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
    if (!StringUtils.hasText(phoneNumber)) return false;

    // 010-1234-1234 형식만 허용
    if (phoneNumber.charAt(3) != '-' || phoneNumber.charAt(8) != '-') return false;
    phoneNumber = phoneNumber.replaceAll("-", "");
    if (phoneNumber.length() != 11) return false;

    return phoneNumber.matches("[0-9]+");
  }
}
