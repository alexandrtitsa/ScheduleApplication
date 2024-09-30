package ua.foxminded.scheduleapp.service;

import ua.foxminded.scheduleapp.model.Schedule;

import java.util.List;

public interface ScheduleService {
	
    List<Schedule> getStudentSchedules();
    
    List<Schedule> getTeacherSchedules();
}
