package org.example.domain.institution.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.institution.controller.request.CreateInstitutionRequest;
import org.example.domain.institution.service.InstitutionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/institution")
@RequiredArgsConstructor
@Tag(name = "InstitutionController", description = "[ADMIN] 기관 관련 API")
public class InstitutionController {

  private final InstitutionService institutionService;

  @GetMapping()
  @Operation(summary = "기관 생성")
  public ApiResponse<Void> createInstitution(CreateInstitutionRequest request) {
    institutionService.createInstitution(request);
    return ApiResponse.onSuccess();
  }
}
