package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converte.StudentConvert;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

@Controller
public class StudentController {

    private StudentService service;
    private StudentConvert convert;

    @Autowired
    public StudentController(StudentService service, StudentConvert convert) {
        this.service = service;
        this.convert = convert;
    }

    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<StudentCourses> studentCourses = service.searchStudentCoursesList();

        model.addAttribute("studentList", convert.convertStudentDetails(students, studentCourses));
        return "studentList";
    }

    @GetMapping("/studentCoursesList")
    public List<StudentCourses> getStudentCoursesList() {
        return service.searchStudentCoursesList();
    }

    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail) {

        service.newStudent(studentDetail.getStudent(), studentDetail.getStudentCourses());
        return "redirect:/studentList";
    }

}
