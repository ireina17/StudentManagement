package raisetech.StudentManagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void before() {
        sut = new StudentService(repository, converter);
    }

    @Test
    void 受講生詳細一覧検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        //事前準備
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

        // 実行
        sut.searchStudentList();

        // 検証
        verify(repository, Mockito.times(1)).search();
        verify(repository, Mockito.times(1)).searchStudentCourseList();
        verify(converter, Mockito.times(1)).convertStudentDetails(studentList, studentCourseList);
    }

    //----------------課題----------------

    //searchStudent
    @Test
    void 受講生検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {

        //事前準備
        String id = "999";
        Student student = new Student();
        student.setId(id);
        when(repository.searchStudent(id)).thenReturn(student);
        when(repository.searchStudentCourse(id)).thenReturn(new ArrayList<>());

        StudentDetail expected = new StudentDetail(student, new ArrayList());

        //実行
        StudentDetail actual = sut.searchStudent(id);

        //検証
        verify(repository, Mockito.times(1)).searchStudent(id);
        verify(repository, Mockito.times(1)).searchStudentCourse(id);
        Assertions.assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
    }

    //registerStudent
    @Test
    void 受講生詳細登録_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        List<StudentCourse> studentCoursesList = List.of(studentCourse);
        StudentDetail studentDetail = new StudentDetail(student, studentCoursesList);

        //実行
        sut.registerStudent(studentDetail);

        verify(repository, Mockito.times(1)).registerStudent(student);
        verify(repository, Mockito.times(1)).registerStudentCourse(studentCourse);
    }

    @Test
    void 受講生詳細更新_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        List<StudentCourse> studentCoursesList = List.of(studentCourse);
        StudentDetail studentDetail = new StudentDetail(student, studentCoursesList);

        //実行
        sut.updateStudent(studentDetail);

        verify(repository, Mockito.times(1)).updateStudent(student);
        verify(repository, Mockito.times(1)).updateStudentCourse(studentCourse);
    }
}