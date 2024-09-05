package ua.foxminded.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.scheduleapp.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
