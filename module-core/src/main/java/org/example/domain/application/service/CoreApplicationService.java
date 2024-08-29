package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.application.Application;
import org.example.domain.application.repository.ApplicationRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreApplicationService {

  private final ApplicationRepository applicationRepository;

  public Application findById(Long id) {
    return applicationRepository.findById(id)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 application id 입니다."));
  }

}
