package ua.foxminded.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.scheduleapp.model.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDate(LocalDate date);
    List<Schedule> findByTeacherId(Long teacherId);
    List<Schedule> findByCourseId(Long courseId);
}
