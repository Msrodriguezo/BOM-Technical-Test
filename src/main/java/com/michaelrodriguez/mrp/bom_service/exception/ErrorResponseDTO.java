package com.michaelrodriguez.mrp.bom_service.exception;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {

    private int status;

    private String message;

    private Map<String, String> errors;
    
    private long timestamp;
}
