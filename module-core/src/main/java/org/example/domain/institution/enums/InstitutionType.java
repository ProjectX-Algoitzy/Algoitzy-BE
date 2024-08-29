package org.example.domain.institution.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstitutionType {
  COMPANY("기업"),
  CAMP("부트캠프");

  private final String name;

}
