package ua.foxminded.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.scheduleapp.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
