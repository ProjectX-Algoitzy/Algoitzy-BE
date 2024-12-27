package org.example.domain.api_request.repository;

import org.example.domain.api_request.ApiRequest;
import org.example.domain.api_request.enums.ApiModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRequestRepository extends JpaRepository<ApiRequest, ApiModule> {

}
