package ua.foxminded.scheduleapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.scheduleapp.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	Optional<Teacher> findByFirstNameAndLastName(String firstName, String lastName);
	
}
