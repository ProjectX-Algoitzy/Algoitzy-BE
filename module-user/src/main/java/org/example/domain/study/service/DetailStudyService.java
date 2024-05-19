package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.ListTempStudyResponse;
import org.example.domain.study.repository.DetailStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailStudyService {

  private final DetailStudyRepository detailStudyRepository;


}
