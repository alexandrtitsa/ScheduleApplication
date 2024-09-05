package ua.foxminded.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.scheduleapp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
