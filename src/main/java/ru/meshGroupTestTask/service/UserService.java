package ru.meshGroupTestTask.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jinq.jpa.JPQL;
import org.jinq.orm.stream.JinqStream;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.meshGroupTestTask.dto.EditUserDto;
import ru.meshGroupTestTask.dto.SaveUserDto;
import ru.meshGroupTestTask.dto.UserDto;
import ru.meshGroupTestTask.entity.Phone;
import ru.meshGroupTestTask.entity.Profile;
import ru.meshGroupTestTask.entity.User;
import ru.meshGroupTestTask.filter.UserFilter;
import ru.meshGroupTestTask.mapper.UserMapper;
import ru.meshGroupTestTask.repo.PhoneRepo;
import ru.meshGroupTestTask.repo.ProfileRepo;
import ru.meshGroupTestTask.repo.UserRepo;
import ru.meshGroupTestTask.utils.JinqHelpper;
import ru.meshGroupTestTask.utils.PagedDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final JinqHelpper jinQHelpper;

    private final UserRepo userRepo;

    private final UserMapper userMapper;

    private final PhoneRepo phoneRepo;

    private final ProfileRepo profileRepo;

    public PagedDto<UserDto> userFilter(UserFilter filter, Pageable pageable){

        JinqStream<User> jinqStream = jinQHelpper.streamAll(User.class);

        if(filter.getName()!=null && !filter.getName().isEmpty()) {
            String s = "%" + filter.getName().toUpperCase() + "%";
            jinqStream = jinqStream.where(c -> JPQL.like(c.getName().toUpperCase(), s));
        }

        String email = filter.getEmail();
        if(email != null && !email.isEmpty()){
            // сравнение через == может ввести в заблуждение, но в рамках jinQ нужно делать так
            jinqStream = jinqStream.where(c-> c.getEmail() == email);
        }

        Long age = filter.getAge();
        if(filter.getAge() != null){
            jinqStream = jinqStream.where(c-> c.getAge() == age);
        }

        String phone = filter.getPhone();
        if(phone != null && !phone.isEmpty()){
            jinqStream = jinqStream.where(c -> JPQL.isIn(phone,
                    JinqStream.from(c.getPhones()).select(Phone::getValue)));
        }

        Long totalCount = jinqStream.count();
        Long pageCount = (totalCount + pageable.getPageSize() - 1) / pageable.getPageSize();

        jinqStream = jinqStream.skip(pageable.getOffset())
                .limit(pageable.getPageSize());

        return new PagedDto<UserDto>(jinqStream.toList().stream()
                .filter(Objects::nonNull)
                .map(userMapper::toUserDto)
                .collect(Collectors.toList())
                , totalCount, pageCount);
    }

    public void saveUser(SaveUserDto dto){
        userRepo.save(userMapper.toUser(dto));
    }

    public void editUser(EditUserDto dto){
        User user = userMapper.toUser(dto);

        userRepo.findById(dto.getId()).ifPresent(oldUser -> {
            user.setPhones(oldUser.getPhones());
//            user.setProfile(oldUser.getProfile());
            ;
//                if(user.getEmail() != null){
//                    if (!emailUnique(user.getEmail(), oldUser)){
//                        throw new RuntimeException("Введенный email занят");
//                    }
//                }
        });
        userRepo.save(user);
    }

    public void addPhoneToUser(String phoneNumber, Long userId){
        userRepo.findById(userId).ifPresent(user -> {
            if(phoneNumber != null){
                if (!phoneUnique(phoneNumber, user)){
                    throw new RuntimeException("Введенный телефон занят");
                }
            }
            Phone newPhone = new Phone();
            newPhone.setValue(phoneNumber);
            newPhone.setUser(user);
            Set<Phone> phones = user.getPhones();
            phones.add(newPhone);
            userRepo.save(user);
        });
    }

    public void removePhoneFromUser(String phoneNumber, Long userId){
        phoneRepo.deletePhoneByNumberAndUser(phoneNumber, userId);
    }

    public void changeCash(BigDecimal cash, Long userId){
        profileRepo.findByUser_id(userId).ifPresentOrElse(
                (profile) -> {
                    profileRepo.updateCash(cash, userId);
                },
                ()-> {
                    Profile profile = new Profile();
                    profile.setCash(cash);
                    profile.setUser(userRepo.findById(userId).orElseThrow(() ->  new RuntimeException("Указанного пользователя не существует")));
                    profileRepo.save(profile);
                }
        );
    }



    private boolean emailUnique(String email, User oldUser){
        Set<User> usersByEmail = userRepo.findByEmail(email);
        usersByEmail.remove(oldUser);
        return usersByEmail.size() == 0;
    }

    private boolean phoneUnique(String phone, User oldUser){
        Set<Phone> phoneCount = phoneRepo.findByUser_id(oldUser.getId());
        phoneCount = phoneCount.stream().filter(p-> p.getUser().getId().equals(oldUser.getId())).collect(Collectors.toSet());
        return phoneCount.size() == 0;
    }

    public void deleteUser(Long id){
        userRepo.findById(id).ifPresent(userRepo::delete);
    }

    public UserDto getUserById(Long id){
        User user = userRepo.getById(id);
        return userMapper.toUserDto(user);
    }

}
