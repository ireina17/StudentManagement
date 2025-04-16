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

    public StudentDetail searchStudent(String id) {
        Student student = repository.searchStudent(id);
        List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentCourses(studentCourses);
        return studentDetail;
    }

    public List<StudentCourses> searchStudentCoursesList() {
        return repository.searchStudentCoursesList();
    }

    @Transactional
    public void newStudent(StudentDetail studentDetail) {

        repository.registerStudent(studentDetail.getStudent());
        for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            studentCourse.setCourseStart(LocalDateTime.now());
            studentCourse.setCourseEnd(LocalDateTime.now().plusYears(1));
            repository.registerStudentCourses(studentCourse);
        }
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
            repository.updateStudentCourses(studentCourse);
        }
    }

}
