package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;

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

    @Insert("INSERT INTO student(name, kana_name, nickname, email, area, age, sex, remark, isDeleted) " +
            "VALUES(#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, NULL)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

    @Insert("INSERT INTO students_courses( student_id, course_name,course_start,course_end) " +
            "VALUES(#{studentId}, #{courseName}, #{courseStart}, #{courseEnd})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentCourses(StudentCourses studentCourses);

}
