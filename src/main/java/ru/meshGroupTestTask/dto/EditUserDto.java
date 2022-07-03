package ru.meshGroupTestTask.dto;

import lombok.Data;

@Data
public class EditUserDto {
    private Long id;

    private String name;

    private Long age;

    private String email;
}
