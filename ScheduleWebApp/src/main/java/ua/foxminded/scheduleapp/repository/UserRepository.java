package ua.foxminded.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.scheduleapp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);
}
