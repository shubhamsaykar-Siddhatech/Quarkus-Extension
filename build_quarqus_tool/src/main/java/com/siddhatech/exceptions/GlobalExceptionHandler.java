package com.siddhatech.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<GlobalException> {
    @Override
    public Response toResponse(GlobalException exception) {
        return Response.status(exception.getErrorCode())
                .entity(new ErrorResponse(exception.getErrorCode(),exception.getDeveloperMsg(),exception.getMsg()))
                .build();
    }
}
