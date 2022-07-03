package ru.meshGroupTestTask.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.meshGroupTestTask.dto.IncreaseUserCashDto;
import ru.meshGroupTestTask.entity.User;
import ru.meshGroupTestTask.repo.UserRepo;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class IncreaseUserCashService {

    public final UserRepo userRepo;

    private final Map<User, IncreaseUserCashDto> usersWithMaxCash;


    @PostConstruct
    private void init(){
        List<User> users = userRepo.findAll();
        users.forEach(user -> {

            BigDecimal currentCash = user.getProfile().getCash();
            BigDecimal maxCash = currentCash.add(currentCash.multiply(BigDecimal.valueOf(1.07)));
            BigDecimal fixedStepOfIncrease = currentCash.multiply(BigDecimal.valueOf(0.1));
            usersWithMaxCash.put(user, new IncreaseUserCashDto(maxCash, fixedStepOfIncrease));

        });
        log.info("usersWithMaxCash.toString()");
    }

    @Scheduled(cron = "*/20 * * * * *")
    private void increaseUserCash(){
        List<User> pretendentsToRemove = new ArrayList<>();
        usersWithMaxCash.forEach((user, dto) -> {
            BigDecimal userCashPlusStep = user.getProfile().getCash().add(dto.getFixedStepOfIncrease());
            if(userCashPlusStep.compareTo(dto.getMaxCash()) < 0){
                BigDecimal currentCash = user.getProfile().getCash();
                log.info("\n name: " + user.getName() + "\n currentCash: " + currentCash + "\n currentCashPlusPercent: "
                        + userCashPlusStep + "\n max: " + dto.getMaxCash());
                user.getProfile().setCash(currentCash.add(dto.getFixedStepOfIncrease()));
                userRepo.save(user);
            }else {
                log.info("user " + user.getName() + " reached limit of cash");
                pretendentsToRemove.add(user);
            }
        });
        pretendentsToRemove.forEach(usersWithMaxCash::remove);
    }
}










