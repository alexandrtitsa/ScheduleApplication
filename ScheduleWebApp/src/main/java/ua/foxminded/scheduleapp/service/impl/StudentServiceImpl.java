package ua.foxminded.scheduleapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.repository.StudentRepository;
import ua.foxminded.scheduleapp.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id).map(student -> {
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setGroup(studentDetails.getGroup());
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    @Transactional(readOnly = true)
	@Override
	public List<Student> findByGroupId(Long groupId) {
	    return studentRepository.findByGroupId(groupId);
	}
}
