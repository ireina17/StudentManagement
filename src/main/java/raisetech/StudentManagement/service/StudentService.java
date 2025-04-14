package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/StudentList")
    public List<Student> searchStudentList() {
        return repository.search();
    }

    @GetMapping("/StudentCoursesList")
    public List<StudentCourses> searchStudentCoursesList() {
        return repository.searchStudentCourses();
    }

    @GetMapping("/registerStudent")
    public void newStudent(Student student, List<StudentCourses> studentCourses) {

        //htmlでデフォルトに期間を設定が必要。コースによって変動も必要
        StudentCourses studentCourse = studentCourses.get(0);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = currentDateTime.plusMonths(4);

        //set 何かしらのID取得が必要
        studentCourse.setId("9");

        //set
        studentCourse.setStudentId(student.getId());
        studentCourse.setCourseStart(LocalDateTime.now());
        studentCourse.setCourseEnd(endDateTime);

        repository.newRegisterStudent(student.getId(), student.getName(), student.getKanaName(), student.getNickname(), student.getEmail(), student.getArea(), student.getAge(), student.getSex(), student.getRemark());
        repository.newRegisterStudentCourses(studentCourse.getId(), studentCourse.getStudentId(), studentCourse.getCourseName(), studentCourse.getCourseStart(), studentCourse.getCourseEnd());
    }

}
