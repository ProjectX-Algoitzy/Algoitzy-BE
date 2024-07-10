package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
    if (!StringUtils.hasText(phoneNumber)) return false;
    if (phoneNumber.length() != 11) return false;

    return phoneNumber.matches("[0-9]+");
  }
}
