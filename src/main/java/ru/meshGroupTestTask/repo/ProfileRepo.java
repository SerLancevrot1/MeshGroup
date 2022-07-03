package ru.meshGroupTestTask.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.meshGroupTestTask.entity.Profile;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser_id(Long userId);

    @Transactional
    @Modifying
    @Query(value = "update profiles set cash = :cash where user_id =:userId " , nativeQuery = true)
    void updateCash(BigDecimal cash, Long userId);
}
