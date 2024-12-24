package org.example.domain.answer.service;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.request.SearchAnswerRequest;
import org.example.domain.answer.controller.response.ListAnswerDto;
import org.example.domain.answer.controller.response.ListAnswerResponse;
import org.example.domain.answer.controller.response.ListAnswerStatusDto;
import org.example.domain.answer.controller.response.ListAnswerStatusResponse;
import org.example.domain.answer.repository.ListAnswerRepository;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListAnswerService {

  private final ListAnswerRepository listAnswerRepository;

  /**
   * 게시글 카테고리 목록 조회
   */
  public ListAnswerStatusResponse getAnswerStatusList() {
    List<ListAnswerStatusDto> statusList = Stream.of(StudyMemberStatus.values())
      .filter(status -> !status.equals(StudyMemberStatus.TEMP_APPLY))
      .map(status -> ListAnswerStatusDto.builder()
        .code(status.name())
        .name(status.getStatus())
        .build())
      .toList();

    return ListAnswerStatusResponse.builder()
      .statusList(statusList)
      .build();
  }

  /**
   * 작성한 지원서 목록 조회
   */
  public ListAnswerResponse getAnswerList(SearchAnswerRequest request) {
    Page<ListAnswerDto> page = listAnswerRepository.getAnswerList(request);
    // 전형 단계 enum to string
    for (ListAnswerDto dto : page.getContent()) {
      dto.updateStatus(dto.getStatus());
    }

    return ListAnswerResponse.builder()
      .answerList(page.getContent())
      .totalCount(page.getTotalElements())
      .build();
  }
}
