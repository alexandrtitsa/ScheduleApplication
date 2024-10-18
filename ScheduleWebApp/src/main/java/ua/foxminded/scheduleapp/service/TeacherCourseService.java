package ua.foxminded.scheduleapp.service;

import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.model.TeacherCourse;

import java.util.List;
import java.util.Optional;

public interface TeacherCourseService {

    List<TeacherCourse> getAllTeacherCourses();
    
    void addTeacherCourse(TeacherCourse teacherCourse);
    
    void deleteTeacherCourse(Long teacherId, Long courseId, Long groupId);

    TeacherCourse findByTeacherCourseGroupId(Long teacherId, Long courseId, Long groupId);
    
    void updateTeacherCourse(TeacherCourse teacherCourse);

	void addTeacherCourse(Optional<Teacher> teacher, Course course, Group group);
	
}
