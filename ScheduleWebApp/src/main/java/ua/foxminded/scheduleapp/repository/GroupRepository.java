package ua.foxminded.scheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.foxminded.scheduleapp.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
	
    Optional<Group> findByGroupName(String groupName);
	
    @Query("SELECT g.id FROM Group g JOIN TeacherCourse tc ON g.id = tc.group.id WHERE tc.course.id = :courseId")
    List<Long> findGroupIdsByCourseId(@Param("courseId") Long courseId);
    
}
