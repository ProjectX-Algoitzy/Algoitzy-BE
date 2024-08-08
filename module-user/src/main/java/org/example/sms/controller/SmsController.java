package org.example.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.sms.controller.request.CertificationPhoneNumberRequest;
import org.example.sms.service.SmsService;
import org.example.util.IpUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
@Tag(name = "SmsController", description = "SMS 관련 API")
public class SmsController {

  private final SmsService smsService;

  @PostMapping("/certification")
  @Operation(summary = "SMS 인증코드 전송")
  public ApiResponse<Void> sendCertificationPhoneNumber(
      @RequestBody @Valid CertificationPhoneNumberRequest request,
      HttpServletRequest httpServletRequest) {
    smsService.sendCertificationPhoneNumber(request, IpUtils.getClientIp(httpServletRequest));
    return ApiResponse.onSuccess();
  }
}