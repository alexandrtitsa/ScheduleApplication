package ua.foxminded.scheduleapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.repository.CourseRepository;
import ua.foxminded.scheduleapp.service.CourseService;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findByIdOrThrow(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Course with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public Course updateCourse(Long id, Course course) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course with id " + id + " not found");
        }
        course.setId(id);
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course with id " + id + " not found");
        }
        courseRepository.deleteById(id);
    }
}
