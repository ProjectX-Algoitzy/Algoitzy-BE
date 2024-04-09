package org.example.email.controller.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class CertificateEmailRequest {

  @Email
  String email;
}
