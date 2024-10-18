package ua.foxminded.scheduleapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.model.TeacherCourse;
import ua.foxminded.scheduleapp.repository.TeacherCourseRepository;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherCourseServiceImplTest {

    @Mock
    private TeacherCourseRepository teacherCourseRepository;

    @InjectMocks
    private TeacherCourseServiceImpl teacherCourseService;

    private TeacherCourse testTeacherCourse;
    private Teacher testTeacher;
    private Course testCourse;
    private Group testGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testTeacher = new Teacher();
        testTeacher.setId(1L);
        testTeacher.setFirstName("Test Teacher");

        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setCourseName("Test Course");

        testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setGroupName("Test Group");

        testTeacherCourse = new TeacherCourse();
        testTeacherCourse.setTeacher(testTeacher);
        testTeacherCourse.setCourse(testCourse);
        testTeacherCourse.setGroup(testGroup);
    }

    @Test
    void testGetAllTeacherCourses() {
        when(teacherCourseRepository.findAll()).thenReturn(List.of(testTeacherCourse));

        List<TeacherCourse> teacherCourses = teacherCourseService.getAllTeacherCourses();

        assertNotNull(teacherCourses);
        assertEquals(1, teacherCourses.size());
        verify(teacherCourseRepository, times(1)).findAll();
    }

    @Test
    void testAddTeacherCourse() {
        teacherCourseService.addTeacherCourse(testTeacherCourse);

        verify(teacherCourseRepository, times(1)).save(testTeacherCourse);
    }

    @Test
    void testDeleteTeacherCourse() {
        teacherCourseService.deleteTeacherCourse(1L, 1L, 1L);

        verify(teacherCourseRepository, times(1)).deleteByTeacherIdAndCourseIdAndGroupId(1L, 1L, 1L);
    }

    @Test
    void testFindByTeacherCourseGroupId() {
        when(teacherCourseRepository.findByTeacherIdAndCourseIdAndGroupId(1L, 1L, 1L))
                .thenReturn(Optional.of(testTeacherCourse));

        TeacherCourse foundTeacherCourse = teacherCourseService.findByTeacherCourseGroupId(1L, 1L, 1L);

        assertNotNull(foundTeacherCourse);
        assertEquals(testTeacher, foundTeacherCourse.getTeacher());
        verify(teacherCourseRepository, times(1))
                .findByTeacherIdAndCourseIdAndGroupId(1L, 1L, 1L);
    }

    @Test
    void testFindByTeacherCourseGroupIdNotFound() {
        when(teacherCourseRepository.findByTeacherIdAndCourseIdAndGroupId(1L, 1L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> teacherCourseService.findByTeacherCourseGroupId(1L, 1L, 1L));
    }

    @Test
    void testUpdateTeacherCourse() {
        teacherCourseService.updateTeacherCourse(testTeacherCourse);

        verify(teacherCourseRepository, times(1)).save(testTeacherCourse);
    }

    @Test
    void testAddTeacherCourseWithOptional() {
        Optional<Teacher> optionalTeacher = Optional.of(testTeacher);

        teacherCourseService.addTeacherCourse(optionalTeacher, testCourse, testGroup);

        verify(teacherCourseRepository, times(1)).save(any(TeacherCourse.class));
    }

    @Test
    void testAddTeacherCourseWithEmptyOptional() {
        Optional<Teacher> emptyTeacher = Optional.empty();

        assertThrows(IllegalArgumentException.class, () -> teacherCourseService.addTeacherCourse(emptyTeacher, testCourse, testGroup));
    }
}
