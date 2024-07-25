package org.example.domain.workbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.workbook.service.WorkbookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workbook")
@Tag(name = "WorkbookController", description = "[ADMIN] 문제집 관련 API")
public class WorkbookController {

  private final WorkbookService workbookService;

  @Deprecated
  @PostMapping("/auto")
  @Operation(summary = "문제집 자동 생성")
  public ApiResponse<Void> createAutoWorkbook() {
    workbookService.createAutoWorkbook();
    return ApiResponse.onCreate();
  }


}
