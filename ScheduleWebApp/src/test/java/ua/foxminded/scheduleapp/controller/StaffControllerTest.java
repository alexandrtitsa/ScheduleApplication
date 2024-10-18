package ua.foxminded.scheduleapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.model.TeacherCourse;
import ua.foxminded.scheduleapp.service.CourseService;
import ua.foxminded.scheduleapp.service.GroupService;
import ua.foxminded.scheduleapp.service.TeacherCourseService;
import ua.foxminded.scheduleapp.service.TeacherService;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StaffController.class)
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherCourseService teacherCourseService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private GroupService groupService;

    private Teacher teacher;
    private Course course;
    private Group group;
    private TeacherCourse teacherCourse;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John Doe");

        course = new Course();
        course.setId(1L);
        course.setCourseName("Math");

        group = new Group();
        group.setId(1L);
        group.setGroupName("Group A");

        teacherCourse = new TeacherCourse();
        teacherCourse.setTeacher(teacher);
        teacherCourse.setCourse(course);
        teacherCourse.setGroup(group);
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void testShowTeacherCourses() throws Exception {
        when(teacherService.getAllTeachers()).thenReturn(Collections.emptyList());
        when(courseService.getAllCourses()).thenReturn(Collections.emptyList());
        when(groupService.getAllGroups()).thenReturn(Collections.emptyList());
        when(teacherCourseService.getAllTeacherCourses()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/staff/panel"))
               .andExpect(status().isOk())
               .andExpect(view().name("staff/panel"))
               .andExpect(model().attributeExists("teachers"))
               .andExpect(model().attributeExists("courses"))
               .andExpect(model().attributeExists("groups"))
               .andExpect(model().attributeExists("teacherCourses"));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void testAddTeacherCourse() throws Exception {
    	mockMvc.perform(post("/staff/teacher-courses/add")
    	        .with(csrf())
    	        .param("teacher", "1")
    	        .param("courseName", "1")
    	        .param("groupName", "1"))
    	        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void testDeleteTeacherCourse() throws Exception {
    	mockMvc.perform(post("/staff/teacher-courses/delete")
    	        .with(csrf())
    	        .param("teacherId", "1")
    	        .param("courseId", "1")
    	        .param("groupId", "1"))
    	        .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void testShowEditForm() throws Exception {
        when(teacherCourseService.findByTeacherCourseGroupId(1L, 1L, 1L)).thenReturn(teacherCourse);
        when(courseService.getAllCourses()).thenReturn(List.of(course));
        when(groupService.getAllGroups()).thenReturn(List.of(group));
        when(teacherService.getAllTeachers()).thenReturn(List.of(teacher));

        mockMvc.perform(get("/staff/teacher-courses/edit/1/1/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/edit-teacher-course"))
                .andExpect(model().attributeExists("teacherCourse", "teachers", "courses", "groups"));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void testUpdateTeacherCourse() throws Exception {
        when(teacherCourseService.findByTeacherCourseGroupId(1L, 1L, 1L)).thenReturn(teacherCourse);

        mockMvc.perform(post("/staff/teacher-courses/update")
                .with(csrf())
                .param("teacherId", "1")
                .param("courseId", "1")
                .param("groupId", "1")
                .param("newTeacherId", "2")
                .param("newGroupId", "2"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void testCreateCourse() throws Exception {
        mockMvc.perform(post("/staff/courses/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("courseName", "Test Course")
                .param("description", "Test Description"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void testCreateGroup() throws Exception {
        mockMvc.perform(post("/staff/groups/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("group", group))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/staff/panel"));
    }

}
