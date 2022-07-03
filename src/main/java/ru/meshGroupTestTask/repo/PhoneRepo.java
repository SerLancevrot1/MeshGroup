package ru.meshGroupTestTask.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.meshGroupTestTask.entity.Phone;
import ru.meshGroupTestTask.entity.User;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface PhoneRepo extends JpaRepository<Phone, Long> {
    Phone findByValue(String value);
    Set<Phone> findByUser_id(Long userId);

    @Transactional
    @Modifying
    @Query("delete from Phone r where r.value =:phoneNumber and r.user.id =:userId")
    void deletePhoneByNumberAndUser(String phoneNumber, Long userId);
}
