package raisetech.StudentManagement.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        //----------------課題----------------
        //事前準備
        Student student = new Student();
        student.setId("1");

        StudentDetail studentDetail = new StudentDetail();
        when(repository.searchStudent("1")).thenReturn(student);

        studentDetail.setStudent(student);
        studentDetail.setStudentCourseList(studentCourseList);

        //実行
        sut.registerStudent(studentDetail);

        //検証
        verify(repository, Mockito.times(1)).registerStudent(student);

        //実行
        StudentDetail actual = sut.searchStudent("1");

        //検証
        assertEquals(student, actual.getStudent());
        verify(repository, Mockito.times(1)).searchStudent("1");
    }
}