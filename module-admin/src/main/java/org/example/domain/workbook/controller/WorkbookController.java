package org.example.domain.workbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.workbook.controller.request.UpdateWorkbookRequest;
import org.example.domain.workbook.controller.response.DetailWorkbookResponse;
import org.example.domain.workbook.service.WorkbookService;
import org.example.domain.workbook_problem.controller.request.CreateWorkbookProblemRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workbook")
@Tag(name = "WorkbookController", description = "[ADMIN] 문제집 관련 API")
public class WorkbookController {

  private final WorkbookService workbookService;

  @GetMapping("/{workbook-id}")
  @Operation(summary = "문제집 상세 조회")
  public ApiResponse<DetailWorkbookResponse> getWorkbook(
    @PathVariable("workbook-id") Long workbookId) {
    return ApiResponse.onSuccess(workbookService.getWorkbook(workbookId));
  }

  @PatchMapping("/{workbook-id}")
  @Operation(summary = "문제집 이름 수정")
  public ApiResponse<Void> updateWorkbook(
    @PathVariable("workbook-id") Long workbookId,
    @RequestBody UpdateWorkbookRequest request) {
    workbookService.updateWorkbook(workbookId, request);
    return ApiResponse.onSuccess();
  }

  @DeleteMapping("/{workbook-id}")
  @Operation(summary = "문제집 삭제")
  public ApiResponse<Void> deleteWorkbook(
    @PathVariable("workbook-id") Long workbookId) {
    workbookService.deleteWorkbook(workbookId);
    return ApiResponse.onSuccess();
  }

  @PostMapping("/{workbook-id}/problem")
  @Operation(summary = "문제집 문제 추가")
  public ApiResponse<Void> createWorkbookProblem(
    @PathVariable("workbook-id") Long workbookId,
    @RequestBody @Valid CreateWorkbookProblemRequest request) {
    workbookService.createWorkbookProblem(workbookId, request);
    return ApiResponse.onCreate();
  }

  @DeleteMapping("/{workbook-id}/problem/{problem-number}")
  @Operation(summary = "문제집 문제 삭제")
  public ApiResponse<Void> deleteWorkbookProblem(
    @PathVariable("workbook-id") Long workbookId,
    @PathVariable("problem-number") Integer problemNumber) {
    workbookService.deleteWorkbookProblem(workbookId, problemNumber);
    return ApiResponse.onSuccess();
  }


}
