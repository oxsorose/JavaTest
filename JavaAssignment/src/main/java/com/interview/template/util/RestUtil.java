package com.interview.template.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.interview.template.model.RestResponse;


public final class RestUtil {
    private RestUtil() {}

    public static <T> ResponseEntity<RestResponse<T>> successResponse(T data, String message, HttpStatus statusCode) {
        return new ResponseEntity<>(new RestResponse<>(Boolean.TRUE, data, message), statusCode);
    }
}
