package com.twelvegg.aicc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello", description = "Test Hello Controller")
public class HelloController {

    @Operation(summary = "Get Hello", description = "Returns a hello message")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Swagger!";
    }
}
