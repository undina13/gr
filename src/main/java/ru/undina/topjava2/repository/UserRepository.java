package ru.undina.topjava2.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.undina.topjava2.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}