package ua.foxminded.scheduleapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.repository.TeacherRepository;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
    }

    @Test
    void getAllTeachers_shouldReturnListOfTeachers() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        List<Teacher> teachers = teacherService.getAllTeachers();

        assertFalse(teachers.isEmpty());
        assertEquals(1, teachers.size());
        assertEquals("John", teachers.get(0).getFirstName());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void getTeacherById_shouldReturnTeacherIfExists() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Optional<Teacher> foundTeacher = teacherService.getTeacherById(1L);

        assertTrue(foundTeacher.isPresent());
        assertEquals("John", foundTeacher.get().getFirstName());
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    void getTeacherById_shouldReturnEmptyIfTeacherDoesNotExist() {

        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Teacher> foundTeacher = teacherService.getTeacherById(1L);

        assertTrue(foundTeacher.isEmpty());
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    void createTeacher_shouldSaveAndReturnTeacher() {
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher savedTeacher = teacherService.createTeacher(teacher);

        assertEquals("John", savedTeacher.getFirstName());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void updateTeacher_shouldUpdateExistingTeacher() {
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setFirstName("Jane");
        updatedTeacher.setLastName("Smith");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherService.updateTeacher(1L, updatedTeacher);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void updateTeacher_shouldThrowExceptionIfTeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setFirstName("Jane");
        updatedTeacher.setLastName("Smith");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            teacherService.updateTeacher(1L, updatedTeacher);
        });

        assertEquals("Teacher with id 1 not found", exception.getMessage());
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(0)).save(any(Teacher.class));
    }

    @Test
    void deleteTeacher_shouldCallRepositoryDeleteById() {
        teacherService.deleteTeacher(1L);

        verify(teacherRepository, times(1)).deleteById(1L);
    }
}
