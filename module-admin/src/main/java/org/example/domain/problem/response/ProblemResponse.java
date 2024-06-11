package org.example.domain.problem.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "백문 문제 크롤링 API 응답 dto")
public class ProblemResponse<T> {

    @Schema(description = "개수")
    private int count;

    @Schema(description = "json으로 제공되는 api 응답값")
    private List<T> items;

}
