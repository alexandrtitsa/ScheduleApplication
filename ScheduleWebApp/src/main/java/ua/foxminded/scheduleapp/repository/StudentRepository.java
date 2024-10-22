package ua.foxminded.scheduleapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.model.User;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
    Optional<User> findByEmail(String email);
    
    List<Student> findByGroupId(Long groupId);
	
    @Query("SELECT s FROM Student s WHERE s.group.id IN :groupIds")
    List<Student> findStudentsByGroupIds(@Param("groupIds") List<Long> groupIds);
    
}
