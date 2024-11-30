package org.example.domain.board_file.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 상세 첨부파일 목록 객체")
public class ListBoardFileDto {

    @Schema(description = "첨부파일명")
    private String originalName;

    @Schema(description = "S3 파일 경로")
    private String fileUrl;

}
