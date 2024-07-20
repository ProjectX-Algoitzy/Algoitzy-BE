package org.example.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.sms.controller.request.CertificationPhoneNumberRequest;
import org.example.sms.service.SmsService;
import org.example.sms_bandwidth.SmsRequestLimiter;
import org.springframework.http.HttpStatus;
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
  public ApiResponse<Boolean> sendCertificationPhoneNumber(@RequestBody @Valid CertificationPhoneNumberRequest request,
      HttpServletRequest httpServletRequest) {
    String ipAddress = httpServletRequest.getRemoteAddr();
    return ApiResponse.onSuccess(smsService.isAllowedToSendSmS(request, ipAddress));
  }
}

/*
 기존 작성 코드
  public ApiResponse<Void> sendCertificationPhoneNumber2(@RequestBody @Valid CertificationPhoneNumberRequest request,
    HttpServletRequest httpServletRequest) {
  String ipAddress = httpServletRequest.getRemoteAddr();
  if (smsRequestLimiter.isAllowedToSendSms(ipAddress)) { // isAllowed .. -> bucket관련 메소드(현재 tryConsumeBucket)
    smsService.sendCertificationPhoneNumber(request);
    return ApiResponse.onSuccess();
  } else {
    return ApiResponse.onFailure("SMS 전송 횟수 초과", HttpStatus.TOO_MANY_REQUESTS.toString(), null);
  }

  */


