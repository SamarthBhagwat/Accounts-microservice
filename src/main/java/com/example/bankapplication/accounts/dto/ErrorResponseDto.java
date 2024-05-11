package com.example.bankapplication.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Error Response Schema"
)
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path on which client sent request"
    )
    private String apiPath;

    @Schema(
            description = "HTTP code for error"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message"
    )
    private String errorMessage;

    @Schema(
            description = "Time at which error occurred"
    )
    private LocalDateTime errorTime;
}
