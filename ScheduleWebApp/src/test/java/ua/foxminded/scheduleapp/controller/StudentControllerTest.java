package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.scheduleapp.model.Schedule;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.service.ScheduleService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    @WithMockUser(username = "user1", roles = {"STUDENT"})
    void testShowStudentSchedule() throws Exception {

        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");
        
        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Smith");

        Schedule schedule1 = new Schedule();
        schedule1.setCourseName("Math");
        schedule1.setTeacher(teacher1);
        
        Schedule schedule2 = new Schedule();
        schedule2.setCourseName("History");
        schedule2.setTeacher(teacher2);

        List<Schedule> schedules = Arrays.asList(schedule1, schedule2);

        Mockito.when(scheduleService.getStudentSchedules()).thenReturn(schedules);

        mockMvc.perform(get("/student/schedule"))
               .andExpect(status().isOk())
               .andExpect(view().name("student/schedule"))
               .andExpect(model().attributeExists("schedules"))
               .andExpect(model().attribute("schedules", schedules));
    }
}
