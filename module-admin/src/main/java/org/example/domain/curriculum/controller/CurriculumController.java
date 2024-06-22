package org.example.domain.curriculum.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.curriculum.controller.request.CreateCurriculumRequest;
import org.example.domain.curriculum.controller.request.SearchCurriculumRequest;
import org.example.domain.curriculum.controller.request.UpdateCurriculumRequest;
import org.example.domain.curriculum.controller.response.DetailCurriculumResponse;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.example.domain.curriculum.service.CurriculumService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curriculum")
@RequiredArgsConstructor
@Tag(name = "CurriculumController", description = "[ADMIN] 커리큘럼 관련 API")
public class CurriculumController {

  private final CurriculumService curriculumService;

  @PostMapping
  @Operation(summary = "커리큘럼 생성")
  public ApiResponse<Void> createCurriculum(@RequestBody @Valid CreateCurriculumRequest request) {
    curriculumService.createCurriculum(request);
    return ApiResponse.onCreate();
  }

  @GetMapping()
  @Operation(summary = "커리큘럼 목록 조회")
  public ApiResponse<ListCurriculumResponse> getCurriculumList(
    @ParameterObject @ModelAttribute @Valid SearchCurriculumRequest request) {
    return ApiResponse.onSuccess(curriculumService.getCurriculumList(request));
  }

  @GetMapping("/{curriculum-id}")
  @Operation(summary = "커리큘럼 상세 조회")
  public ApiResponse<DetailCurriculumResponse> getCurriculum(@PathVariable("curriculum-id") Long curriculumId) {
    return ApiResponse.onSuccess(curriculumService.getCurriculum(curriculumId));
  }

  @PatchMapping("/{curriculum-id}")
  @Operation(summary = "커리큘럼 수정")
  public ApiResponse<Void> updateCurriculum(
    @PathVariable("curriculum-id") Long curriculumId,
    @RequestBody @Valid UpdateCurriculumRequest request
  ) {
    curriculumService.updateCurriculum(curriculumId, request);
    return ApiResponse.onSuccess();
  }

  @DeleteMapping("/{curriculum-id}")
  @Operation(summary = "커리큘럼 삭제")
  public ApiResponse<Void> deleteCurriculum(
    @PathVariable("curriculum-id") Long curriculumId
  ) {
    curriculumService.deleteCurriculum(curriculumId);
    return ApiResponse.onSuccess();
  }

}
