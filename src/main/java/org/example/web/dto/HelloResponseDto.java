package org.example.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor    // final필드가 포함된 생성자를 생성
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
