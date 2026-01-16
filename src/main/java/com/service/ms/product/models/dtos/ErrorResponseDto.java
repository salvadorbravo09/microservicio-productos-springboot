package com.service.ms.product.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponseDto {
    private String message;
    private int statusCode;
    private String status;
    private LocalDateTime timestamp;
    private String path;
}
