package raisetech.StudentManagement.controller.converte;

import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentConvert {

    public List<StudentDetail> convertStudentDetails(List<Student> students, List<StudentCourses> studentCourses) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        for (Student student : students) {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);
            List<StudentCourses> convertStudentCourses = studentCourses.stream()
                    .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
                    .collect(Collectors.toList());
            studentDetail.setStudentCourses(convertStudentCourses);
            studentDetails.add(studentDetail);
        }
        return studentDetails;
    }
}
