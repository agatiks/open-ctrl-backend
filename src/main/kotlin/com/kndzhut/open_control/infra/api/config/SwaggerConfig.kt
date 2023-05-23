package com.kndzhut.open_control.infra.api.config

import com.kndzhut.open_control.infra.api.AppointmentsController
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    companion object {
        private const val CURRENT_API_VERSION = "1"
        private const val SWAGGER_TITLE = "API открытый контроль"
        private const val APPOINTMENTS_API_GROUP_NAME = "appointments"
        private const val SELECTION_API_GROUP_NAME = "users-selection"
    }

    @Bean
    fun springShopOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info()
                    .title(SWAGGER_TITLE)
                    .version(CURRENT_API_VERSION)
            )
    }

    @Bean
    fun cohortOpenApiGroup(): GroupedOpenApi {
        return GroupedOpenApi
            .builder()
            .group(APPOINTMENTS_API_GROUP_NAME)
            .packagesToScan(AppointmentsController::class.java.packageName)
            .build()
    }
}