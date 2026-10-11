package com.openconsigplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "OpenConsigPlatform API",
                version = "v1",
                description = "API para simulações internas de crédito consignado. "
                        + "Esta versão não realiza autenticação, contratação ou integração bancária."
        )
)
public class OpenConsigPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenConsigPlatformApplication.class, args);
    }
}
