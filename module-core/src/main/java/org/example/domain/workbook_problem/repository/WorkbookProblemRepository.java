package org.example.domain.workbook_problem.repository;

import org.example.domain.workbook_problem.WorkbookProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface WorkbookProblemRepository extends JpaRepository<WorkbookProblem, Long> {

  @Modifying
  void deleteByWorkbookIdAndProblemNumber(Long workbookId, Integer number);
}
