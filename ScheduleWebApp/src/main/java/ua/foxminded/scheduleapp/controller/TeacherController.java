package ua.foxminded.scheduleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.foxminded.scheduleapp.service.ScheduleService;

@Controller
public class TeacherController {

    private final ScheduleService scheduleService;

    @Autowired
    public TeacherController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/teacher/schedule")
    public String showTeacherSchedule(Model model) {
        model.addAttribute("schedules", scheduleService.getTeacherSchedules());
        return "/teacher/schedule";
    }

}
