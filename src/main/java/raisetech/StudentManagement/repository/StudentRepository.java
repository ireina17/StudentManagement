package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.*;
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

    @Select("SELECT * FROM student WHERE id =#{id}")
    Student searchStudent(String id);

    @Select("SELECT * FROM students_courses")
    List<StudentCourses> searchStudentCoursesList();

    @Select("SELECT * FROM students_courses WHERE student_id =#{studentId}")
    List<StudentCourses> searchStudentCourses(String studentId);


    @Insert("INSERT INTO student(name, kana_name, nickname, email, area, age, sex, remark, is_Deleted) " +
            "VALUES(#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

    @Insert("INSERT INTO students_courses( student_id, course_name,course_start,course_end) " +
            "VALUES(#{studentId}, #{courseName}, #{courseStart}, #{courseEnd})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentCourses(StudentCourses studentCourses);

    @Update("UPDATE student SET name=#{name}, kana_name =#{kanaName}, nickname=#{nickname}, email=#{email}, area=#{area}, age=#{age}, sex=#{sex}, remark=#{remark}, is_Deleted=#{isDeleted} WHERE id=#{id}")
    void updateStudent(Student student);

    @Update("UPDATE students_courses SET course_name=#{courseName} WHERE id =#{id} ")
    void updateStudentCourses(StudentCourses studentCourses);

}
