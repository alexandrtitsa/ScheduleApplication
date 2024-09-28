package ua.foxminded.scheduleapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.repository.CourseRepository;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setId(1L);
        course.setCourseName("Mathematics");
    }

    @Test
    void getAllCourses_shouldReturnListOfCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(course));

        List<Course> courses = courseService.getAllCourses();

        assertFalse(courses.isEmpty());
        assertEquals(1, courses.size());
        assertEquals("Mathematics", courses.get(0).getCourseName());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void getCourseById_shouldReturnCourseIfExists() {

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getCourseById(1L);

        assertTrue(result.isPresent());
        assertEquals("Mathematics", result.get().getCourseName());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void getCourseById_shouldReturnEmptyIfCourseDoesNotExist() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Course> result = courseService.getCourseById(1L);

        assertFalse(result.isPresent());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdOrThrow_shouldThrowExceptionIfCourseNotFound() {

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> courseService.findByIdOrThrow(1L));
        assertEquals("Course with id 1 not found", exception.getMessage());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void createCourse_shouldSaveCourse() {

        when(courseRepository.save(course)).thenReturn(course);

        Course createdCourse = courseService.createCourse(course);

        assertNotNull(createdCourse);
        assertEquals("Mathematics", createdCourse.getCourseName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void updateCourse_shouldUpdateCourseIfExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(courseRepository.save(course)).thenReturn(course);

        Course updatedCourse = courseService.updateCourse(1L, course);

        assertNotNull(updatedCourse);
        assertEquals(1L, updatedCourse.getId());
        verify(courseRepository, times(1)).existsById(1L);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void updateCourse_shouldThrowExceptionIfCourseDoesNotExist() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> courseService.updateCourse(1L, course));
        assertEquals("Course with id 1 not found", exception.getMessage());
        verify(courseRepository, times(1)).existsById(1L);
        verify(courseRepository, never()).save(course);
    }

    @Test
    void deleteCourse_shouldDeleteCourseIfExists() {

        when(courseRepository.existsById(1L)).thenReturn(true);

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).existsById(1L);
        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCourse_shouldThrowExceptionIfCourseDoesNotExist() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(1L));
        assertEquals("Course with id 1 not found", exception.getMessage());
        verify(courseRepository, times(1)).existsById(1L);
        verify(courseRepository, never()).deleteById(1L);
    }
}
