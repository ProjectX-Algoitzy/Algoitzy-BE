package org.example.domain.algorithm.repository;

import org.example.domain.algorithm.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, String> {

}
