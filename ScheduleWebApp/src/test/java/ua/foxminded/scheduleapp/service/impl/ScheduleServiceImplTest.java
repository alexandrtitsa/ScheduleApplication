package ua.foxminded.scheduleapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.model.Schedule;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.repository.ScheduleRepository;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private Schedule schedule;
    private Course course;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        course = new Course();
        course.setId(1L);
        course.setCourseName("Mathematics");

        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        schedule = new Schedule();
        schedule.setId(1L);
        schedule.setCourse(course);
        schedule.setTeacher(teacher);
    }

    @Test
    void getStudentSchedules_shouldReturnListOfSchedules() {
        when(scheduleRepository.findAll()).thenReturn(List.of(schedule));

        List<Schedule> schedules = scheduleService.getStudentSchedules();

        assertFalse(schedules.isEmpty());
        assertEquals(1, schedules.size());
        assertEquals("Mathematics", schedules.get(0).getCourse().getCourseName());
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    void getTeacherSchedules_shouldReturnListOfSchedules() {
        when(scheduleRepository.findAll()).thenReturn(List.of(schedule));

        List<Schedule> schedules = scheduleService.getTeacherSchedules();

        assertFalse(schedules.isEmpty());
        assertEquals(1, schedules.size());
        assertEquals("John Doe", schedules.get(0).getTeacher().getFirstName() + " " + schedules.get(0).getTeacher().getLastName());
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    void getStudentSchedules_shouldReturnEmptyListIfNoSchedules() {
        when(scheduleRepository.findAll()).thenReturn(List.of());

        List<Schedule> schedules = scheduleService.getStudentSchedules();

        assertTrue(schedules.isEmpty());
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    void getTeacherSchedules_shouldReturnEmptyListIfNoSchedules() {
        when(scheduleRepository.findAll()).thenReturn(List.of());

        List<Schedule> schedules = scheduleService.getTeacherSchedules();

        assertTrue(schedules.isEmpty());
        verify(scheduleRepository, times(1)).findAll();
    }
}

