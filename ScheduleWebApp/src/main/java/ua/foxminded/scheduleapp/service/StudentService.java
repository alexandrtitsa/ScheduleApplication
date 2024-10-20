package ua.foxminded.scheduleapp.service;

import ua.foxminded.scheduleapp.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> listStudents();

    Optional<Student> getStudentById(Long id);

    Student createStudent(Student student);

    Student updateStudent(Long id, Student studentDetails);

    void deleteStudent(Long id);
    
    void updateStudent(Student student);

	List<Student> findByGroupId(Long groupId);
}
