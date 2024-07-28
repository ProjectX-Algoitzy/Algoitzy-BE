package org.example.domain.workbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.workbook.controller.response.DetailWorkbookResponse;
import org.example.domain.workbook.service.WorkbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workbook")
@Tag(name = "WorkbookController", description = "[USER] 문제집 관련 API")
public class WorkbookController {

  private final WorkbookService workbookService;

  @GetMapping("/{workbook-id}")
  @Operation(summary = "문제집 상세 조회")
  public ApiResponse<DetailWorkbookResponse> getWorkbook(
    @PathVariable("workbook-id") Long workbookId) {
    return ApiResponse.onSuccess(workbookService.getWorkbook(workbookId));
  }


}
