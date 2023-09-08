package com.modak.challenge.configuration;

import com.modak.challenge.dto.ErrorResponseDto;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springdoc.core.utils.Constants.SPRINGDOC_SWAGGER_UI_ENABLED;

@Configuration
@ConditionalOnProperty(value = SPRINGDOC_SWAGGER_UI_ENABLED, matchIfMissing = true)
public class SwaggerConfiguration {


    @Bean
    public OpenAPI configureOpenAPI() {
        OpenAPI oas = new OpenAPI();
        oas.info(new Info()
                .title("Rate-Limited Notification Service")
                .description("Email Notification")
                .contact(new Contact().name("Matias Thwaites").email("thwaiatesmatias@gmail.com")));

        oas.components(new Components().addSchemas("ErrorResponse", getSchema(ErrorResponseDto.class)));

        return oas;
    }

    @Bean
    public OperationCustomizer customizeOperation() {
        return (operation, handlerMethod) ->
                operation
                        .responses(
                                operation
                                        .getResponses()
                                        .addApiResponse("422", new ApiResponse().description("Unprocessable Entity").content(
                                                new Content().addMediaType(
                                                        org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                                                        new MediaType().schema(getSchema(ErrorResponseDto.class)))
                                        ))
                                        .addApiResponse("400", new ApiResponse().description("Bad Request").content(
                                                new Content().addMediaType(
                                                        org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                                                        new MediaType().schema(getSchema(ErrorResponseDto.class)))))
                                        .addApiResponse("500", new ApiResponse().description("Internal Server Error").content(
                                                new Content().addMediaType(
                                                        org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                                                        new MediaType().schema(getSchema(ErrorResponseDto.class)))))
                        );
    }


    private Schema getSchema(Class className) {
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(
                        new AnnotatedType(className).resolveAsRef(false));
        return resolvedSchema.schema;
    }

}
