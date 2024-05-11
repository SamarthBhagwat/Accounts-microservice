package com.example.bankapplication.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Response Schema"
)
@Data @AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status code of response"
    )
    private String statusCode;

    @Schema(
            description = "Status message of response"
    )
    private String statusMsg;
}
