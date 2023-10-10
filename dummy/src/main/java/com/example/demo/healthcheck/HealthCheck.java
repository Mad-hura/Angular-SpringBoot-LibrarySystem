package com.example.demo.healthcheck;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Controller;

@Controller
public class HealthCheck  implements HealthIndicator {

    long freeMemory = Runtime.getRuntime().freeMemory();
    long totalMemory = Runtime.getRuntime().totalMemory();
    double freeMemoryPercent = ((double) freeMemory / (double) totalMemory) * 100;
    public boolean healthCheck(){

        if (freeMemoryPercent > 25) {
            return true;
        }
            return false;
    }

    @Override
    public Health health() {
        if(healthCheck()){
            return Health.up().withDetail("Percentage of Free memory = ", freeMemoryPercent + "%")
                    .build();
        }
        return Health.down().withDetail("Percentage of Free memory = ", freeMemoryPercent + "%")
                .build();
    }
}
