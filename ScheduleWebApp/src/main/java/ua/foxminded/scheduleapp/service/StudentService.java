package ua.foxminded.scheduleapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public Optional<Student> getStudentById(Long id) {
		return studentRepository.findById(id);
	}

	public Student createStudent(Student student) {
		return studentRepository.save(student);
	}

	public Student updateStudent(Long id, Student studentDetails) {
		return studentRepository.findById(id).map(student -> {
			student.setFirstName(studentDetails.getFirstName());
			student.setLastName(studentDetails.getLastName());
			student.setGroup(studentDetails.getGroup());
			return studentRepository.save(student);
		}).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
	}

	public void deleteStudent(Long id) {
		studentRepository.deleteById(id);
	}

	@GetMapping("/students/new")
	public String showCreateForm(Model model) {
		model.addAttribute("student", new Student());
		return "students/create";
	}
}
