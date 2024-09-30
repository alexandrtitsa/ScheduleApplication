package ua.foxminded.scheduleapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.model.User;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<User> findByEmail(String email);
}
