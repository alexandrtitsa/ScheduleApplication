package ua.foxminded.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.scheduleapp.model.TeacherCourse;

import java.util.Optional;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {
	
    Optional<TeacherCourse> findByTeacherIdAndCourseIdAndGroupId(Long teacherId, Long courseId, Long groupId);

    void deleteByTeacherIdAndCourseIdAndGroupId(Long teacherId, Long courseId, Long groupId);
    
}
