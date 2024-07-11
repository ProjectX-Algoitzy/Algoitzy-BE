package org.example.schedule.solved_ac_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "백문 문제 API 응답 객체")
public class ProblemResponse {

    @Schema(description = "총 문제 개수")
    private int count;

    @Schema(description = "문제 목록")
    @JsonProperty("items")
    private List<ProblemDto> problemList;

}
