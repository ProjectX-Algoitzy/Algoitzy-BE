package org.example.domain.inquiry.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InquiryCategory {
    FEATURE("기능 제안"),
    USAGE("이용 문의"),
    ERROR("오류 신고"),
    STUDY("스터디 관련"),
    ETC("기타");

    private final String name;
}