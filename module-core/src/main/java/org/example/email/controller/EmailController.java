package org.example.email.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.ApiResponse;
import org.example.email.controller.request.CertificationEmailRequest;
import org.example.email.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "EmailController", description = "이메일 관련 API")
public class EmailController {

  private final EmailService emailService;

  @PostMapping("/certification")
  @Operation(summary = "[USER] 이메일 인증코드 전송")
  public ApiResponse<Void> sendCertificationEmail(@RequestBody @Valid CertificationEmailRequest request) {
    emailService.sendCertificationEmail(request);
    return ApiResponse.onSuccess();
  }

}
