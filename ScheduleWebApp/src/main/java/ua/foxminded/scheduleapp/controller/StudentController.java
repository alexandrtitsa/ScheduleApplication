package ua.foxminded.scheduleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.foxminded.scheduleapp.service.ScheduleService;

@Controller
public class StudentController {

    private final ScheduleService scheduleService;

    @Autowired
    public StudentController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/student/schedule")
    public String showStudentSchedule(Model model) {
        model.addAttribute("schedules", scheduleService.getStudentSchedules());
        return "student/schedule";
    }
}
