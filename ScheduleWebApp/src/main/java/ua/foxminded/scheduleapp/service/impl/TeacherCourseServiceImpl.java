package ua.foxminded.scheduleapp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.model.TeacherCourse;
import ua.foxminded.scheduleapp.repository.TeacherCourseRepository;
import ua.foxminded.scheduleapp.service.TeacherCourseService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherCourseServiceImpl implements TeacherCourseService {

    private final TeacherCourseRepository teacherCourseRepository;

    public TeacherCourseServiceImpl(TeacherCourseRepository teacherCourseRepository) {
        this.teacherCourseRepository = teacherCourseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherCourse> getAllTeacherCourses() {
        return teacherCourseRepository.findAll();
    }

    @Override
    @Transactional
    public void addTeacherCourse(TeacherCourse teacherCourse) {
        teacherCourseRepository.save(teacherCourse);
    }

    @Override
    @Transactional
    public void deleteTeacherCourse(Long teacherId, Long courseId, Long groupId) {
        teacherCourseRepository.deleteByTeacherIdAndCourseIdAndGroupId(teacherId, courseId, groupId);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherCourse findByTeacherCourseGroupId(Long teacherId, Long courseId, Long groupId) {
        return teacherCourseRepository.findByTeacherIdAndCourseIdAndGroupId(teacherId, courseId, groupId)
                .orElseThrow(() -> new IllegalArgumentException("TeacherCourse not found"));
    }

    @Override
    @Transactional
    public void updateTeacherCourse(TeacherCourse teacherCourse) {
        teacherCourseRepository.save(teacherCourse);
    }

    @Override
    @Transactional
    public void addTeacherCourse(Optional<Teacher> teacher, Course course, Group group) {
        if (teacher.isEmpty()) {
            throw new IllegalArgumentException("Teacher not found");
        }

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setTeacher(teacher.get());
        teacherCourse.setCourse(course);
        teacherCourse.setGroup(group);

        teacherCourseRepository.save(teacherCourse);
    }

}
