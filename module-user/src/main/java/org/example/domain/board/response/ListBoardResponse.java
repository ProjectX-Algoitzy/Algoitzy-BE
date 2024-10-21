package org.example.domain.board.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.board.Board;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시판 목록 응답 객체")
public class ListBoardResponse {

    @Default
    List<Board> BoardList = new ArrayList<>();

}
