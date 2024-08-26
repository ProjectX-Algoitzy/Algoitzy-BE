package org.example.health;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.util.http_request.Url;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthCheckController {

    private final WebDriver webDriver;

    @CrossOrigin("*")
    @GetMapping()
    @Operation(hidden = true)
    public String healthCheck() {
        return "I'm healthy";
    }

    @GetMapping("/selenium")
    @Deprecated
    @Operation(summary = "크롤링 테스트용", description = "반환값 : 김두현 백준 문제 풀이 수")
    public ApiResponse<Integer> seleniumCheck() {
        webDriver.get(Url.BAEKJOON_USER.getBaekjoonUserUrl("engus525"));
        return ApiResponse.onSuccess(Integer.parseInt(webDriver.findElement(By.id("u-solved")).getText()));
    }

}
