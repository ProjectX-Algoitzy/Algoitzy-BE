package org.example.domain.board.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardCategory {
    NOTICE("공지"),
    FREE("자유"),
    QUESTION("질문"),
    INFO("정보"),
    PROMOTION("홍보");

    private final String name;
}