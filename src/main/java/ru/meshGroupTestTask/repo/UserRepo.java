package ru.meshGroupTestTask.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meshGroupTestTask.entity.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Set<User> findByEmail(String email);
}
