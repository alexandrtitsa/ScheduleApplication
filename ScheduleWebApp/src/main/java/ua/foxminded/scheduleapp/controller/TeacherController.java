package ua.foxminded.scheduleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.service.GroupService;
import ua.foxminded.scheduleapp.service.ScheduleService;
import ua.foxminded.scheduleapp.service.StudentService;

import java.util.List;

@Controller
public class TeacherController {

    private final ScheduleService scheduleService;
    private final GroupService groupService;
    private final StudentService studentService;

    @Autowired
    public TeacherController(ScheduleService scheduleService, GroupService groupService, StudentService studentService) {
        this.scheduleService = scheduleService;
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping("/teacher/schedule")
    public String showTeacherSchedule(Model model) {
        model.addAttribute("schedules", scheduleService.getTeacherSchedules());
        return "/teacher/schedule";
    }

    @GetMapping("/teacher/course/{courseId}/groups")
    public String showGroupsAndStudents(@PathVariable Long courseId, Model model) {
        List<Long> groupIds = groupService.getGroupIdsByCourseId(courseId);
        
        List<Student> students = studentService.getStudentsByGroupIds(groupIds);

        model.addAttribute("students", students);
        return "/teacher/groups-students";
    }
}
