package ru.meshGroupTestTask.dto;

import lombok.Data;

@Data
public class SaveUserDto {
    private String name;

    private Long age;

    private String email;
}
