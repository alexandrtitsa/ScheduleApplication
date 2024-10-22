package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.model.Schedule;
import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.service.GroupService;
import ua.foxminded.scheduleapp.service.ScheduleService;
import ua.foxminded.scheduleapp.service.StudentService;  // Додаємо StudentService

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private StudentService studentService;

    @Test
    @WithMockUser(username = "teacher1", roles = {"TEACHER"})
    void testShowTeacherSchedule() throws Exception {
        Course course1 = new Course();
        course1.setCourseName("Mathematics");

        Schedule schedule1 = new Schedule();
        schedule1.setCourse(course1);

        Course course2 = new Course();
        course2.setCourseName("Physics");

        Schedule schedule2 = new Schedule();
        schedule2.setCourse(course2);

        List<Schedule> schedules = Arrays.asList(schedule1, schedule2);

        Mockito.when(scheduleService.getTeacherSchedules()).thenReturn(schedules);

        mockMvc.perform(get("/teacher/schedule"))
                .andExpect(status().isOk())
                .andExpect(view().name("/teacher/schedule"))
                .andExpect(model().attributeExists("schedules"))
                .andExpect(model().attribute("schedules", schedules));
    }
    
    @Test
    @WithMockUser(username = "teacher1", roles = {"TEACHER"})
    void testShowGroupsAndStudents() throws Exception {
        Long courseId = 1L;

        List<Long> groupIds = Arrays.asList(1L, 2L);
        Mockito.when(groupService.getGroupIdsByCourseId(courseId)).thenReturn(groupIds);

        Group group1 = new Group();
        group1.setGroupName("Group 1");

        Student student1 = new Student();
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setEmail("john.doe@example.com");
        student1.setGroup(group1);

        Student student2 = new Student();
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setEmail("jane.smith@example.com");
        student2.setGroup(group1);

        List<Student> students = Arrays.asList(student1, student2);
        Mockito.when(studentService.getStudentsByGroupIds(groupIds)).thenReturn(students);

        mockMvc.perform(get("/teacher/course/{courseId}/groups", courseId))
                .andExpect(status().isOk())
                .andExpect(view().name("/teacher/groups-students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("students", students));
    }
}
