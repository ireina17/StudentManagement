package raisetech.StudentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converte.StudentConvert;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.util.Arrays;
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
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentCourses(Arrays.asList(new StudentCourses()));
        model.addAttribute("studentDetail", studentDetail);
        return "registerStudent";
    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }

        service.newStudent(studentDetail);
        return "redirect:/studentList";
    }

    //updeta 解説
    //受講生更新ページ　a=tag
    //id情報で検索して取れてきたデータを入力して更新をする

    @GetMapping("/searchIdStudent")
    public String updateIdStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "updateStudent";
    }

    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "updateStudent";
        }

        service.newStudent(studentDetail);
        return "updateStudent:/studentList";
    }
}
