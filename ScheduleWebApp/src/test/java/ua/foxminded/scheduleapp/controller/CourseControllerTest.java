package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.service.CourseService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void testListCourses() throws Exception {
        Course course1 = new Course();
        Course course2 = new Course();

        Mockito.when(courseService.listCourses()).thenReturn(Arrays.asList(course1, course2));

        mockMvc.perform(get("/courses"))
            .andExpect(status().isOk())
            .andExpect(view().name("courses/list"))
            .andExpect(model().attributeExists("courses"))
            .andExpect(model().attribute("courses", Arrays.asList(course1, course2)));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/courses/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("courses/create"))
            .andExpect(model().attributeExists("course"));
    }

    @Test
    void testSaveCourse() throws Exception {
        mockMvc.perform(post("/courses/new")
            .param("courseName", "Biology")
            .param("description", "Biology Course"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/courses"));

        Mockito.verify(courseService).createCourse(any(Course.class));
    }

    @Test
    void testShowEditForm() throws Exception {
        Course course = new Course();

        Mockito.when(courseService.findByIdOrThrow(1L)).thenReturn(course);

        mockMvc.perform(get("/courses/edit/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("courses/edit"))
            .andExpect(model().attributeExists("course"))
            .andExpect(model().attribute("course", course));
    }

    @Test
    void testUpdateCourse() throws Exception {
        mockMvc.perform(post("/courses/edit/1")
            .param("courseName", "Updated Course")
            .param("description", "Updated Description"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/courses"));

        Mockito.verify(courseService).updateCourse(eq(1L), any(Course.class));
    }

    @Test
    void testDeleteCourse() throws Exception {
        mockMvc.perform(get("/courses/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/courses"));

        Mockito.verify(courseService).deleteCourse(1L);
    }
}
