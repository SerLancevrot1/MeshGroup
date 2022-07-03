package ru.meshGroupTestTask.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    private String name;

    private Long age;

    private String email;

    private Set<String> phones;

    private BigDecimal cash;
}
