package com.fleetguard360.presentation.advice;

import com.fleetguard360.service.exception.InvalidCredentialsException;
import feign.FeignException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.module.FindException;

@ControllerAdvice
public class GraphqlExceptionHandler {

    @GraphQlExceptionHandler(InvalidCredentialsException.class)
    public GraphQLError handlerInvalidCredentials(InvalidCredentialsException ex) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(ErrorType.UNAUTHORIZED)
                .build();

    }

    @GraphQlExceptionHandler(FeignException.class)
    public GraphQLError handlerFeignException(FindException ex) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }
}
