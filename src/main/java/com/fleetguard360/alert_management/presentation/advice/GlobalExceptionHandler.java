package com.fleetguard360.alert_management.presentation.advice;

import com.fleetguard360.alert_management.service.exception.BadRequestException;
import com.fleetguard360.alert_management.service.exception.ConflictException;
import com.fleetguard360.alert_management.service.exception.NotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        String traceId = UUID.randomUUID().toString();

        int status;
        String code;
        String message = ex.getMessage();
        List<Map<String, String>> details = new ArrayList<>();
        Map<String, Object> extensions = new LinkedHashMap<>();

        if (ex instanceof BadRequestException) {
            status = 400;
            code = "BAD_REQUEST";
        } else if (ex instanceof ConflictException) {
            status = 409;
            code = "CONFLICT";
        } else if (ex instanceof NotFoundException) {
            status = 404;
            code = "NOT_FOUND";
        } else if (ex instanceof ConstraintViolationException cve) {
            status = 400;
            code = "BAD_REQUEST";

            // Lista de errores detallados
            details = cve.getConstraintViolations().stream()
                    .map(v -> Map.of(
                            "field", v.getPropertyPath().toString(),
                            "message", v.getMessage()
                    ))
                    .toList();

            // Mensaje general
            message = "Error de validación en uno o más campos";

        } else {
            status = 500;
            code = "INTERNAL_ERROR";
            message = (message != null) ? message : "Unexpected error";
        }

        extensions.put("code", code);
        extensions.put("status", status);
        extensions.put("timestamp", OffsetDateTime.now().toString());
        extensions.put("traceId", traceId);
        extensions.put("field", env.getField().getName());
        if (!details.isEmpty()) extensions.put("details", details);

        return GraphqlErrorBuilder.newError(env)
                .message(message)
                .extensions(extensions)
                .build();
    }
}
