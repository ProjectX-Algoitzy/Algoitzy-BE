package org.example.domain.rule.repository;

import org.example.domain.rule.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {

}
