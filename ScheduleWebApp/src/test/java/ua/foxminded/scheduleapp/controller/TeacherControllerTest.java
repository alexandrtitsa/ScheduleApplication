package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.service.TeacherService;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    void testListTeachers() throws Exception {
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();

        Mockito.when(teacherService.listTeachers()).thenReturn(Arrays.asList(teacher1, teacher2));

        mockMvc.perform(get("/teachers"))
            .andExpect(status().isOk())
            .andExpect(view().name("teachers/list"))
            .andExpect(model().attributeExists("teachers"))
            .andExpect(model().attribute("teachers", Arrays.asList(teacher1, teacher2)));
    }
}
