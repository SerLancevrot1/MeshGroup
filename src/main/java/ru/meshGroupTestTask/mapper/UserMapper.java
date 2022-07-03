package ru.meshGroupTestTask.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.meshGroupTestTask.dto.EditUserDto;
import ru.meshGroupTestTask.dto.SaveUserDto;
import ru.meshGroupTestTask.dto.UserDto;
import ru.meshGroupTestTask.entity.Phone;
import ru.meshGroupTestTask.entity.User;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE , imports = {Phone.class, Collectors.class})
public interface UserMapper {

    @Mapping(target = "phones", ignore = true)
    User toUser(UserDto u);

    User toUser(EditUserDto u);

    User toUser(SaveUserDto u);

    @Mapping(target = "cash", source = "u.profile.cash")
    @Mapping(target = "phones", expression = "java(u.getPhones().stream().map(Phone::getValue).collect(Collectors.toSet()))")
    UserDto toUserDto(User u);
}
