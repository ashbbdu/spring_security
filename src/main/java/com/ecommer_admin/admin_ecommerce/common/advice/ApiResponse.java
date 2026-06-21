package com.ecommer_admin.admin_ecommerce.common.advice;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse <T> {
    private LocalDateTime timeStamp;
    private Boolean success;
    private String message;
    private ApiError error;
    private T data;


    ApiResponse () {
        this.timeStamp = LocalDateTime.now();
    }

    ApiResponse (ApiError error) {
        this();
        this.success = false;
        this.message = "Something went wrong";
        this.error = error;
    }

    ApiResponse (T data) {
        this();
        this.success = true;
        this.message = "Successful Operation";
        this.data = data;
    }

}
