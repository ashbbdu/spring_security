package com.ecommer_admin.admin_ecommerce.common.advice;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
//@NullMarked
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

//    @Override
//    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        if(body instanceof ApiResponse<?>) {
//            return body;
//        }
//        return new ApiResponse<>(body);
//    }
@Override
public Object beforeBodyWrite(Object body,
                              MethodParameter returnType,
                              MediaType selectedContentType,
                              Class<? extends HttpMessageConverter<?>> selectedConverterType,
                              ServerHttpRequest request,
                              ServerHttpResponse response) {

    String path = request.getURI().getPath();

    if (path.startsWith("/v3/api-docs")
            || path.startsWith("/swagger-ui")) {
        return body;
    }

    if (body instanceof ApiResponse<?>) {
        return body;
    }

    return new ApiResponse<>(body);
}
}
