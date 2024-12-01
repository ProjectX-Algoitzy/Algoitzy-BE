package org.example.domain.reply.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 댓글 목록 조회 응답 객체")
public class ListReplyDto {

    @Schema(description = "댓글 ID")
    private long replyId;

    @Schema(description = "부모 댓글 ID")
    private Long parentReplyId;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자 백준 닉네임")
    private String handle;

    @Schema(description = "작성자")
    private String createdName;

    @Schema(description = "작성일")
    private LocalDateTime createdTime;

    @Schema(description = "내 댓글 여부")
    private boolean myReplyYn;

    @Schema(description = "게시판 작성자 여부")
    private boolean myBoardYn;

    @Schema(description = "작성자 프로필 이미지 URL")
    private String profileUrl;

    @Schema(description = "내 좋아요 여부")
    private boolean myLikeYn;

    @Schema(description = "댓글 깊이")
    private int depth;

    @Schema(description = "삭제 여부")
    private boolean deleteYn;

    @Schema(description = "관리자 삭제 여부")
    private boolean deleteByAdminYn;

    @Default
    @Schema(description = "자식 댓글 목록")
    private List<ListReplyDto> childrenReplyList = new ArrayList<>();

}
