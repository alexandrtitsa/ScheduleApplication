package ua.foxminded.scheduleapp.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.service.GroupService;
import ua.foxminded.scheduleapp.service.StudentService;

public class StaffControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StaffController staffController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(staffController).build();
    }

    @Test
    public void testShowCreateGroupForm() throws Exception {

        Group group1 = new Group();
        group1.setId(1L);
        group1.setGroupName("Group 1");

        Group group2 = new Group();
        group2.setId(2L);
        group2.setGroupName("Group 2");

        Student student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Jane");
        student2.setLastName("Doe");

        List<Group> groups = Arrays.asList(group1, group2);
        List<Student> students = Arrays.asList(student1, student2);

        when(groupService.getAllGroups()).thenReturn(groups);
        when(studentService.listStudents()).thenReturn(students);

        mockMvc.perform(get("/staff/groups/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/create-group"))
                .andExpect(model().attributeExists("group"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("groups", groups))
                .andExpect(model().attribute("students", students));

        verify(groupService).getAllGroups();
        verify(studentService).listStudents();
    }

    @Test
    public void testChangeStudentGroup() throws Exception {
        Group newGroup = new Group();
        newGroup.setId(1L);
        newGroup.setGroupName("New Group");

        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");

        when(studentService.getStudentById(anyLong())).thenReturn(Optional.of(student));
        when(groupService.getGroupById(anyLong())).thenReturn(Optional.of(newGroup));

        mockMvc.perform(post("/staff/students/change-group")
                .param("studentId", "1")
                .param("groupId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/staff/groups/create"));

        verify(studentService).updateStudent(student);
    }

    @Test
    public void testDeleteGroup() throws Exception {
        Group defaultGroup = new Group();
        defaultGroup.setId(1L);
        defaultGroup.setGroupName("Group 1");

        Student student1 = new Student();
        student1.setId(1L);
        student1.setGroup(defaultGroup);

        when(groupService.findByName("Group 1")).thenReturn(Optional.of(defaultGroup));
        when(studentService.findByGroupId(anyLong())).thenReturn(Arrays.asList(student1));

        mockMvc.perform(post("/staff/groups/delete")
                .param("groupId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/staff/groups/create"));

        verify(groupService).deleteGroup(2L);
        verify(studentService).updateStudent(student1);
    }
}
