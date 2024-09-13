package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.service.CourseService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void testListCourses() throws Exception {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setCourseName("Mathematics");
        course1.setDescription("Math Course");

        Course course2 = new Course();
        course2.setId(2L);
        course2.setCourseName("Physics");
        course2.setDescription("Physics Course");

        when(courseService.getAllCourses()).thenReturn(Arrays.asList(course1, course2));

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

        verify(courseService).createCourse(any(Course.class));
    }

    @Test
    void testShowEditForm() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Mathematics");
        course.setDescription("Math Course");

        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

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

        verify(courseService).updateCourse(eq(1L), any(Course.class));
    }

    @Test
    void testDeleteCourse() throws Exception {
        mockMvc.perform(get("/courses/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/courses"));

        verify(courseService).deleteCourse(1L);
    }
}
