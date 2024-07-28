package org.example.domain.institution.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.institution.controller.request.SearchInstitutionRequest;
import org.example.domain.institution.controller.response.DetailInstitutionResponse;
import org.example.domain.institution.controller.response.ListInstitutionResponse;
import org.example.domain.institution.service.InstitutionService;
import org.example.domain.workbook.controller.response.ListInstitutionWorkbookResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/institution")
@RequiredArgsConstructor
@Tag(name = "InstitutionController", description = "[USER] 기관 관련 API")
public class InstitutionController {

  private final InstitutionService institutionService;

  @GetMapping()
  @Operation(summary = "기관 목록 조회")
  public ApiResponse<ListInstitutionResponse> getInstitutionList(
    @ParameterObject @ModelAttribute @Valid SearchInstitutionRequest request
  ) {
    return ApiResponse.onSuccess(institutionService.getInstitutionList(request));
  }

  @GetMapping("/{institution-id}")
  @Operation(summary = "기관 상세 조회")
  public ApiResponse<DetailInstitutionResponse> getInstitution(
    @PathVariable("institution-id") Long institutionId
  ) {
    return ApiResponse.onSuccess(institutionService.getInstitution(institutionId));
  }

  @GetMapping("/{institution-id}/workbook")
  @Operation(summary = "기관 문제집 목록 조회")
  public ApiResponse<ListInstitutionWorkbookResponse> getInstitutionWorkbookList(
    @PathVariable("institution-id") Long institutionId) {
    return ApiResponse.onCreate(institutionService.getInstitutionWorkbookList(institutionId));
  }
}
