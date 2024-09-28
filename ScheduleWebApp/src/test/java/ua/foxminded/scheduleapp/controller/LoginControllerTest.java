package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void defaultAfterLogin_shouldRedirectAdminToAdminPanel() throws Exception {
        mockMvc.perform(get("/default"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/admin/panel"));
    }

    @Test
    @WithMockUser(username = "student", roles = {"STUDENT"})
    void defaultAfterLogin_shouldRedirectStudentToStudentSchedule() throws Exception {
        mockMvc.perform(get("/default"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/student/schedule"));
    }

    @Test
    @WithMockUser(username = "teacher", roles = {"TEACHER"})
    void defaultAfterLogin_shouldRedirectTeacherToTeacherSchedule() throws Exception {
        mockMvc.perform(get("/default"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/teacher/schedule"));
    }

    @Test
    @WithMockUser(username = "user")
    void defaultAfterLogin_shouldRedirectToRoot() throws Exception {
        mockMvc.perform(get("/default"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/"));
    }
}
