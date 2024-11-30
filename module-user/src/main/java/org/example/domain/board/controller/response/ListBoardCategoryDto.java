package org.example.domain.board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카테고리 목록 조회 응답 DTO")
public class ListBoardCategoryDto {

    @Schema(description = "카테고리 enum code")
    private String code;

    @Schema(description = "카테고리명")
    private String name;

}
