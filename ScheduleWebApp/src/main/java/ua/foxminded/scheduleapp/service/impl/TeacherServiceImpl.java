package ua.foxminded.scheduleapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.scheduleapp.model.Teacher;
import ua.foxminded.scheduleapp.repository.TeacherRepository;
import ua.foxminded.scheduleapp.service.TeacherService;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    @Transactional
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public Teacher updateTeacher(Long id, Teacher teacher) {
        return teacherRepository.findById(id).map(existingTeacher -> {
            existingTeacher.setFirstName(teacher.getFirstName());
            existingTeacher.setLastName(teacher.getLastName());
            return teacherRepository.save(existingTeacher);
        }).orElseThrow(() -> new IllegalArgumentException("Teacher with id " + id + " not found"));
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
