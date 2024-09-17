package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.service.StudentService;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testListStudents() throws Exception {
        Student student1 = new Student();
        Student student2 = new Student();

        Mockito.when(studentService.listStudents()).thenReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/students"))
            .andExpect(status().isOk())
            .andExpect(view().name("students/list"))
            .andExpect(model().attributeExists("students"))
            .andExpect(model().attribute("students", Arrays.asList(student1, student2)));
    }
}
