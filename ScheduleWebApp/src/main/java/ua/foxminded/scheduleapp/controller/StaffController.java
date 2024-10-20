package ua.foxminded.scheduleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ua.foxminded.scheduleapp.model.Course;
import ua.foxminded.scheduleapp.model.Group;
import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.model.TeacherCourse;
import ua.foxminded.scheduleapp.service.CourseService;
import ua.foxminded.scheduleapp.service.GroupService;
import ua.foxminded.scheduleapp.service.StudentService;
import ua.foxminded.scheduleapp.service.TeacherCourseService;
import ua.foxminded.scheduleapp.service.TeacherService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final TeacherCourseService teacherCourseService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final StudentService studentService;

    @Autowired
    public StaffController(TeacherCourseService teacherCourseService,
                           TeacherService teacherService,
                           CourseService courseService,
                           GroupService groupService,
                           StudentService studentService) {
        this.teacherCourseService = teacherCourseService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping("/panel")
    public String showTeacherCourses(Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        List<Course> courses = courseService.getAllCourses();
        List<Group> groups = groupService.getAllGroups();

        model.addAttribute("teachers", teachers);
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);

        List<TeacherCourse> teacherCourses = teacherCourseService.getAllTeacherCourses();
        model.addAttribute("teacherCourses", teacherCourses);

        return "staff/panel";
    }

    @PostMapping("/teacher-courses/add")
    public String addTeacherCourse(@RequestParam("teacher") Long teacherId, 
                                   @RequestParam("courseName") Long courseId, 
                                   @RequestParam("groupName") Long groupId, 
                                   Model model) {

        Optional<Teacher> teacherOptional = teacherService.getTeacherById(teacherId);
        if (!teacherOptional.isPresent()) {
            model.addAttribute("errorMessage", "Teacher not found with ID: " + teacherId);
            return "staff/error";
        }

        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        if (!courseOptional.isPresent()) {
            model.addAttribute("errorMessage", "Course not found with ID: " + courseId);
            return "staff/error";
        }

        Optional<Group> groupOptional = groupService.getGroupById(groupId);
        if (!groupOptional.isPresent()) {
            model.addAttribute("errorMessage", "Group not found with ID: " + groupId);
            return "staff/error";
        }

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setTeacher(teacherOptional.get());
        teacherCourse.setCourse(courseOptional.get());
        teacherCourse.setGroup(groupOptional.get());

        teacherCourseService.addTeacherCourse(teacherCourse);

        return "redirect:/staff/panel";
    }

    @PostMapping("/teacher-courses/delete")
    public String deleteTeacherCourse(@RequestParam Long teacherId, @RequestParam Long courseId, @RequestParam Long groupId) {
        teacherCourseService.deleteTeacherCourse(teacherId, courseId, groupId);
        return "redirect:/staff/panel";
    }

    @GetMapping("/teacher-courses/edit/{teacherId}/{courseId}/{groupId}")
    public String showEditForm(@PathVariable("teacherId") Long teacherId,
                               @PathVariable("courseId") Long courseId,
                               @PathVariable("groupId") Long groupId,
                               Model model) {
        TeacherCourse teacherCourse = teacherCourseService.findByTeacherCourseGroupId(teacherId, courseId, groupId);
        List<Course> courses = courseService.getAllCourses();
        List<Group> groups = groupService.getAllGroups();
        List<Teacher> teachers = teacherService.getAllTeachers();

        model.addAttribute("teacherCourse", teacherCourse);
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);
        model.addAttribute("teachers", teachers);

        return "staff/edit-teacher-course";
    }

    @PostMapping("/teacher-courses/update")
    public String updateTeacherCourse(@RequestParam("teacherId") Long teacherId, 
                                      @RequestParam("courseId") Long courseId, 
                                      @RequestParam("groupId") Long groupId, 
                                      @RequestParam("newTeacherId") Long newTeacherId, 
                                      @RequestParam("newGroupId") Long newGroupId) {

        TeacherCourse teacherCourse = teacherCourseService.findByTeacherCourseGroupId(teacherId, courseId, groupId);

        Optional<Teacher> newTeacherOptional = teacherService.getTeacherById(newTeacherId);
        if (newTeacherOptional.isPresent()) {
            teacherCourse.setTeacher(newTeacherOptional.get());
        }

        Optional<Group> newGroupOptional = groupService.getGroupById(newGroupId);
        if (newGroupOptional.isPresent()) {
            teacherCourse.setGroup(newGroupOptional.get());
        }

        teacherCourseService.updateTeacherCourse(teacherCourse);

        return "redirect:/staff/panel";
    }

    @GetMapping("/courses/create")
    public String showCreateCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "staff/create-course";
    }

    @PostMapping("/courses/create")
    public String createCourse(@RequestParam("courseName") String courseName,
                               @RequestParam("description") String description, Model model) {
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDescription(description);

        courseService.createCourse(course);
        return "redirect:/staff/panel";
    }

    @GetMapping("/groups/create")
    public String showCreateGroupForm(Model model) {
        model.addAttribute("group", new Group());
        
        List<Group> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        
        List<Student> students = studentService.listStudents();
        model.addAttribute("students", students);
        
        return "staff/create-group";
    }
    
    @PostMapping("/groups/create")
    public String createGroup(@ModelAttribute Group group) {
        groupService.createGroup(group);
        return "redirect:/staff/panel";
    }
    
    @PostMapping("/students/change-group")
    public String changeStudentGroup(@RequestParam("studentId") Long studentId, 
                                     @RequestParam("groupId") Long groupId, 
                                     Model model) {
        Optional<Student> studentOptional = studentService.getStudentById(studentId);
        if (!studentOptional.isPresent()) {
            model.addAttribute("errorMessage", "Student not found with ID: " + studentId);
            return "staff/error";
        }

        Optional<Group> groupOptional = groupService.getGroupById(groupId);
        if (!groupOptional.isPresent()) {
            model.addAttribute("errorMessage", "Group not found with ID: " + groupId);
            return "staff/error";
        }

        Student student = studentOptional.get();
        student.setGroup(groupOptional.get());

        studentService.updateStudent(student);

        return "redirect:/staff/groups/create";
    }
    
    @PostMapping("/groups/delete")
    public String deleteGroup(@RequestParam Long groupId) {
        Group defaultGroup = groupService.findByName("Group 1")
                .orElseThrow(() -> new IllegalStateException("Default group 'Group 1' not found"));

        List<Student> studentsInGroup = studentService.findByGroupId(groupId);
        
        for (Student student : studentsInGroup) {
            student.setGroup(defaultGroup);
            studentService.updateStudent(student);
        }
        
        groupService.deleteGroup(groupId);

        return "redirect:/staff/groups/create";
    }

}
