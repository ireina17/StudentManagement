package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生を扱うリポジトリ
 * <p>
 * 全件検索や
 */
@Mapper
public interface StudentRepository {

    @Select("SELECT * FROM student")
    List<Student> search();

    @Select("SELECT * FROM students_courses")
    List<StudentCourses> searchStudentCourses();

    @Insert("INSERT INTO student(id, name, kana_name, nickname, email, area, age, sex, remark, isDeleted) " +
            "VALUES(#{id}, #{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, NULL)")
    void newRegisterStudent(String id, String name, String kanaName, String nickname, String email, String area, int age, String sex, String remark);

    @Insert("INSERT INTO students_courses(id, student_id, course_name,course_start,course_end) " +
            "VALUES(#{id}, #{studentId}, #{courseName}, #{courseStart}, #{courseEnd})")
    void newRegisterStudentCourses(String id, String studentId, String courseName, LocalDateTime courseStart, LocalDateTime courseEnd);

}
