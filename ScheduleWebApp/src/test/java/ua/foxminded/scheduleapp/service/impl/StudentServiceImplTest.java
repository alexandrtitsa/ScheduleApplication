package ua.foxminded.scheduleapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private Group group;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        group = new Group();
        group.setId(1L);
        group.setGroupName("Group 1");

        student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setGroup(group);
    }

    @Test
    void listStudents_shouldReturnListOfStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<Student> students = studentService.listStudents();

        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
        assertEquals("John", students.get(0).getFirstName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_shouldReturnStudentIfExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> foundStudent = studentService.getStudentById(1L);

        assertTrue(foundStudent.isPresent());
        assertEquals("John", foundStudent.get().getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void getStudentById_shouldReturnEmptyIfStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Student> foundStudent = studentService.getStudentById(1L);

        assertFalse(foundStudent.isPresent());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void createStudent_shouldSaveAndReturnStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student createdStudent = studentService.createStudent(student);

        assertNotNull(createdStudent);
        assertEquals("John", createdStudent.getFirstName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void updateStudent_shouldUpdateExistingStudent() {
        Student updatedStudentDetails = new Student();
        updatedStudentDetails.setFirstName("Jane");
        updatedStudentDetails.setLastName("Smith");
        updatedStudentDetails.setGroup(group);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student updatedStudent = studentService.updateStudent(1L, updatedStudentDetails);

        assertNotNull(updatedStudent);
        assertEquals("Jane", updatedStudent.getFirstName());
        assertEquals("Smith", updatedStudent.getLastName());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_shouldThrowExceptionIfStudentNotFound() {
        Student updatedStudentDetails = new Student();
        updatedStudentDetails.setFirstName("Jane");
        updatedStudentDetails.setLastName("Smith");

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(1L, updatedStudentDetails);
        });

        assertEquals("Student not found with id 1", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    @Test
    void deleteStudent_shouldCallRepositoryDeleteById() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
