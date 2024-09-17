package ua.foxminded.scheduleapp.service;

import ua.foxminded.scheduleapp.model.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> listCourses();

    Optional<Course> getCourseById(Long id);

    Course findByIdOrThrow(Long id);

    Course createCourse(Course course);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);
}
