package org.example.domain.institution.repository;

import org.example.domain.institution.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

}
