package br.com.microservices.limitsservice.controllers;

import br.com.microservices.limitsservice.bean.LimitsConfiguration;
import br.com.microservices.limitsservice.config.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitsConfiguration retrieveLimitsConfiguration() {
        return new LimitsConfiguration(configuration.getMaximum(), configuration.getMinimum());
    }

    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod = "falbackRetrieveConfiguration")
    public LimitsConfiguration retrieveConfiguration() {
        throw new RuntimeException("Not available");
    }

    public LimitsConfiguration fallbackRetrieveConfiguration() {
        return new LimitsConfiguration(3, 1);
    }

}
