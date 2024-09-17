package ua.foxminded.scheduleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.scheduleapp.model.Student;
import ua.foxminded.scheduleapp.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.listStudents());
		return "students/list";
	}

	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("student", new Student());
		return "students/create";
	}

	@PostMapping("/new")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.createStudent(student);
		return "redirect:/students";
	}
}

