package org.example.domain.page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "PageController", description = "[임시] 페이지 리다이렉트 테스트용 API")
public class PageController {

  @GetMapping("/401")
  @Operation(summary = "401 페이지")
  public ApiResponse<Void> notAuthorized() {
    throw new GeneralException(ErrorStatus.PAGE_UNAUTHORIZED, "401 페이지 테스트");
  }

  @GetMapping("/404")
  @Operation(summary = "404 페이지")
  public ApiResponse<Void> pageNotFound() {
    throw new GeneralException(ErrorStatus.PAGE_NOT_FOUND, "404 페이지 테스트");
  }

}
