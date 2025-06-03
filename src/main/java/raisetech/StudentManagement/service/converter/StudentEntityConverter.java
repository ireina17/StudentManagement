package raisetech.StudentManagement.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです。
 */
@Component
public class StudentEntityConverter {

    private StudentRepository repository;

    @Autowired
    public StudentEntityConverter(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * 受講生に紐づく受講生コース情報をDBから取得しマッピングする。
     * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
     *
     * @param students 受講生一覧
     * @return 受講生詳細情報のリスト
     */
    public List<StudentDetail> convertStudentDetail(List<Student> students) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        for (Student student : students) {
            StudentDetail studentDetail = new StudentDetail(student, repository.searchStudentCourse(student.getId()));
            studentDetails.add(studentDetail);
        }
        return studentDetails;
    }
}
