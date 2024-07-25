package org.example.domain.institution.enums;

import java.util.Arrays;

public enum InstitutionType {
  COMPANY,
  CAMP;

  public static boolean exist(String institutionType) {
    return Arrays.stream(values()).anyMatch(type -> type.toString().equals(institutionType));
  }
}
