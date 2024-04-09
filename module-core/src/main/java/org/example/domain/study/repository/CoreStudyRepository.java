package org.example.domain.study.repository;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.study.Study;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreStudyRepository {

  private final StudyRepository studyRepository;

  public Study findById(Long id) {
    return studyRepository.findById(id)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 study id 입니다."));
  }

}
