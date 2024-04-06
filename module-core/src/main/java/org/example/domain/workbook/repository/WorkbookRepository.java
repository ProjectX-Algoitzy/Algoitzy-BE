package org.example.domain.workbook.repository;

import org.example.domain.workbook.Workbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkbookRepository extends JpaRepository<Workbook, Long> {

}
