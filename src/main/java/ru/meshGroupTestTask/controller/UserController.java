package ru.meshGroupTestTask.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.meshGroupTestTask.dto.EditUserDto;
import ru.meshGroupTestTask.dto.SaveUserDto;
import ru.meshGroupTestTask.dto.UserDto;
import ru.meshGroupTestTask.filter.UserFilter;
import ru.meshGroupTestTask.service.UserService;
import ru.meshGroupTestTask.utils.PagedDto;

import java.math.BigDecimal;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("filter")
    public PagedDto<UserDto> userFilter(
            @RequestBody UserFilter filter,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        log.info("filter:" +filter);
        return service.userFilter(filter, PageRequest.of(page, size));
    }

    @PostMapping("save")
    public void saveUser(@RequestBody SaveUserDto dto){
        service.saveUser(dto);
    }

    @PostMapping("edit")
    public void saveUser(@RequestBody EditUserDto dto){
        service.editUser(dto);
    }

    @PostMapping("add-phone")
    public void addPhoneToUser(@RequestParam String phoneNumber,
                               @RequestParam Long userId){
        service.addPhoneToUser(phoneNumber, userId);
    }

    @DeleteMapping("remove-phone")
    public void removePhoneFromUser(@RequestParam String phoneNumber,
                                    @RequestParam Long userId){
        service.removePhoneFromUser(phoneNumber, userId);
    }

    @PostMapping("change-cash")
    public void changeCashOfUser(@RequestParam BigDecimal cash,
                                 @RequestParam Long userId){
        service.changeCash(cash, userId);
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id){
        service.deleteUser(id);
    }

}
