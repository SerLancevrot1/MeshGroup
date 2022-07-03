package ru.meshGroupTestTask.filter;

import lombok.Data;

@Data
public class UserFilter {

    private final String name;

    private final Long age;

    private final String email;

    private final String phone;
}
