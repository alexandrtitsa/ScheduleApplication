package ua.foxminded.scheduleapp.service;

import ua.foxminded.scheduleapp.model.Teacher;
import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<Teacher> getAllTeachers();

    Optional<Teacher> getTeacherById(Long id);

    Teacher createTeacher(Teacher teacher);

    Teacher updateTeacher(Long id, Teacher teacher);

    void deleteTeacher(Long id);

}
