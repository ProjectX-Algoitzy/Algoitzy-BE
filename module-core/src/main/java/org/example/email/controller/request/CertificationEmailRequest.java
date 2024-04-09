package org.example.email.controller.request;

import jakarta.validation.constraints.Email;

public record CertificationEmailRequest(
  @Email
  String email) {
}
