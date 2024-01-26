package com.hakan.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorMessage {
    int code;
    String message;
    List<String> fields;
    @Builder.Default
    LocalDateTime dateTime = LocalDateTime.now();
}
