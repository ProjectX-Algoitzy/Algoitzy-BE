package org.example.email.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.email.controller.request.SendEmailRequest;
import org.example.email.controller.request.ValidateEmailRequest;
import org.example.email.service.CoreEmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "EmailController", description = "이메일 관련 API")
public class EmailController {

  private final CoreEmailService coreEmailService;

  @PostMapping()
  @Operation(summary = "이메일 전송")
  public ApiResponse<Void> sendEmail(@RequestBody @Valid SendEmailRequest request) {
    coreEmailService.sendEmail(request);
    return ApiResponse.onSuccess();
  }

  @PostMapping("/certification")
  @Operation(summary = "이메일 인증")
  public ApiResponse<Void> validateEmail(@RequestBody @Valid ValidateEmailRequest request) {
    coreEmailService.validateEmail(request);
    return ApiResponse.onSuccess();
  }

}
