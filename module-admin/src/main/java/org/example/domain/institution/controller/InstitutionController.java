package org.example.domain.institution.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.institution.controller.request.CreateInstitutionRequest;
import org.example.domain.institution.controller.request.SearchInstitutionRequest;
import org.example.domain.institution.controller.response.ListInstitutionResponse;
import org.example.domain.institution.service.InstitutionService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/institution")
@RequiredArgsConstructor
@Tag(name = "InstitutionController", description = "[ADMIN] 기관 관련 API")
public class InstitutionController {

  private final InstitutionService institutionService;

  @PostMapping()
  @Operation(summary = "기관 생성")
  public ApiResponse<Void> createInstitution(@RequestBody @Valid CreateInstitutionRequest request) {
    institutionService.createInstitution(request);
    return ApiResponse.onSuccess();
  }

  @GetMapping()
  @Operation(summary = "기관 목록 조회")
  public ApiResponse<ListInstitutionResponse> getInstitutionList(
    @ParameterObject @ModelAttribute @Valid SearchInstitutionRequest request
  ) {
    return ApiResponse.onSuccess(institutionService.getInstitutionList(request));
  }
}
