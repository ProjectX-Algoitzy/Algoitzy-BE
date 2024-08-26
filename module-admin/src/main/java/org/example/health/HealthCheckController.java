package org.example.health;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @CrossOrigin("*")
    @GetMapping("/health")
    @Operation(hidden = true)
    public String healthCheck() {
        return "I'm healthy";
    }

}
