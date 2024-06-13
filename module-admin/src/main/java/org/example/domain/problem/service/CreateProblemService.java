package org.example.domain.problem.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.language.bm.Lang;
import org.example.domain.problem.Level;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ProblemRepository;
import org.example.schedule.solved_ac_response.LanguageDto;
import org.example.schedule.solved_ac_response.ProblemDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProblemService {

  private final ProblemRepository problemRepository;

  // 2. problem table에 저장
  public void saveProblemList(List<ProblemDto> problemDtoList) {
    for (ProblemDto problemDto : problemDtoList) {
      Problem problem = createProblem(problemDto);
      problemRepository.save(problem);
    }
  }

  private Problem createProblem(ProblemDto problemDto) {

    List<String> languageList = problemDto.getLanguageList().stream()
        .map(LanguageDto::getLanguage)
        .collect(Collectors.toList());

    return Problem.builder()
          .number(problemDto.getNumber())
          .name(problemDto.getName())
          .level(Level.valueOf(String.valueOf(problemDto.getLevel())))
          .languageList(languageList)
          .build();
  }

  private List<String> getLanguageList(List<LanguageDto> languageDtoList) {
    return languageDtoList.stream()
        .map(LanguageDto::getLanguage)
        .collect(Collectors.toList());
  }

}
