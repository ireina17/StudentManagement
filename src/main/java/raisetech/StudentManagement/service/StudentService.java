package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domain.StudentDetail;
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

    public List<Student> searchStudentList() {
        return repository.search();
    }

    public List<StudentCourses> searchStudentCoursesList() {
        return repository.searchStudentCourses();
    }

    @Transactional
    public void newStudent(StudentDetail studentDetail) {

        repository.registerStudent(studentDetail.getStudent());
        //TODO:コース登録を行う
        for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            studentCourse.setCourseStart(LocalDateTime.now());
            studentCourse.setCourseEnd(LocalDateTime.now().plusYears(1));
            repository.registerStudentCourses(studentCourse);
        }
    }

}
