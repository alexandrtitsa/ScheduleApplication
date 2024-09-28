package ua.foxminded.scheduleapp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.scheduleapp.model.Schedule;
import ua.foxminded.scheduleapp.repository.ScheduleRepository;
import ua.foxminded.scheduleapp.service.ScheduleService;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getStudentSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getTeacherSchedules() {
        return scheduleRepository.findAll();
    }
}
