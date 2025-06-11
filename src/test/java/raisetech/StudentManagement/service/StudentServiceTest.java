package raisetech.StudentManagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.controller.dto.request.StudentRequest;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.converter.StudentEntityConverter;

import java.time.LocalDateTime;
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

    @Mock
    private StudentEntityConverter entityConverter;

    private StudentService sut;

    @BeforeEach
    void before() {
        sut = new StudentService(repository, converter, entityConverter);
    }

    @Test
    void 受講生詳細一覧検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        //事前準備
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        StudentRequest studentRequest = new StudentRequest(null, null, null, null, null, null, null, null, null, null);
        when(repository.findStudentsByAll()).thenReturn(studentList);
        when(repository.findStudentCoursesByAll()).thenReturn(studentCourseList);

        // 実行
        sut.findStudents(studentRequest);

        // 検証
        verify(repository, Mockito.times(1)).findStudentsByAll();
        verify(repository, Mockito.times(1)).findStudentCoursesByAll();
        verify(converter, Mockito.times(1)).convertStudentDetails(studentList, studentCourseList);
    }

    @Test
    void 受講生id検索_リポジトリの処理が適切に呼び出せてること() {

        //事前準備
        String id = "999";
        Student student = new Student();
        student.setId(id);
        when(repository.searchStudentById(id)).thenReturn(student);
        when(repository.searchStudentCourseById(id)).thenReturn(new ArrayList<>());

        StudentDetail expected = new StudentDetail(student, new ArrayList());

        //実行
        StudentDetail actual = sut.searchStudentById(id);

        //検証
        verify(repository, Mockito.times(1)).searchStudentById(id);
        verify(repository, Mockito.times(1)).searchStudentCourseById(id);
        Assertions.assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
    }

    @Test
    void 受講生名前検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String name = "田中太郎";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentRequest studentRequest = new StudentRequest(null, name, null, null, null, null, null, null, null, null);

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));

        when(repository.findStudentsByName(name)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsByName(name);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生カナ名検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String kanaName = "タナカタロウ";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentRequest studentRequest = new StudentRequest(null, null, kanaName, null, null, null, null, null, null, null);

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));

        when(repository.findStudentsByKanaName(kanaName)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsByKanaName(kanaName);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生ニックネーム検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String nickname = "タロウ";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentRequest studentRequest = new StudentRequest(null, null, null, nickname, null, null, null, null, null, null);

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));

        when(repository.findStudentsByNickname(nickname)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsByNickname(nickname);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生メール検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String email = "test@example.com";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentRequest studentRequest = new StudentRequest(null, null, null, null, email, null, null, null, null, null);

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));

        when(repository.findStudentsByEmail(email)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsByEmail(email);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生地域検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String area = "東京都";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentRequest studentRequest = new StudentRequest(null, null, null, null, null, area, null, null, null, null);

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));

        when(repository.findStudentsByArea(area)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsByArea(area);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生年齢検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String age = "20";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentRequest studentRequest = new StudentRequest(null, null, null, null, null, null, age, null, null, null);

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));

        when(repository.findStudentsByAge(age)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsByAge(age);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生性別検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String sex = "男性";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentRequest studentRequest = new StudentRequest(null, null, null, null, null, null, null, sex, null, null);

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));

        when(repository.findStudentsBySex(sex)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsBySex(sex);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生備考検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String remark = "テストです。";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentRequest studentRequest = new StudentRequest(null, null, null, null, null, null, null, null, remark, null);

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));

        when(repository.findStudentsByRemark(remark)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsByRemark(remark);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生削除フラグ検索_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        String isDeleted = "true";

        LocalDateTime now = LocalDateTime.now();
        Student student = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "テストです。", false);
        StudentCourse studentCourse = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));

        List<Student> studentList = List.of(student);
        List<StudentDetail> studentDetailList = List.of(new StudentDetail(student, List.of(studentCourse)));
        StudentRequest studentRequest = new StudentRequest(null, null, null, null, null, null, null, null, null, isDeleted);

        when(repository.findStudentsByIsDeleted(isDeleted)).thenReturn(studentList);
        when(entityConverter.convertStudentDetail(studentList)).thenReturn(studentDetailList);

        List<StudentDetail> expected = studentDetailList;
        List<StudentDetail> actual = sut.findStudents(studentRequest);

        verify(repository, Mockito.times(1)).findStudentsByIsDeleted(isDeleted);
        verify(entityConverter, Mockito.times(1)).convertStudentDetail(studentList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void コース申し込み状況一覧検索_リポジトリの処理が適切に呼び出せること() {
        List<CourseStatus> courseStatusesList = new ArrayList<>();
        when((repository.findCourseStatusByAll())).thenReturn(courseStatusesList);

        sut.findCourseStatusByAll();

        verify(repository, Mockito.times(1)).findCourseStatusByAll();
    }

    @Test
    void コース申し込み状況検索_リポジトリの処理が適切に呼び出せること() {
        String id = "999";
        CourseStatus courseStatus = new CourseStatus(id, null, null);
        when(repository.searchCourseStatus(id)).thenReturn(courseStatus);

        CourseStatus expected = new CourseStatus(id, null, null);
        CourseStatus actual = sut.searchCourseStatus(id);

        verify(repository, Mockito.times(1)).searchCourseStatus(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void 受講生詳細登録_リポジトリとコンバーターの処理が適切に呼び出せてること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        List<StudentCourse> studentCoursesList = List.of(studentCourse);
        StudentDetail studentDetail = new StudentDetail(student, studentCoursesList);
        CourseStatus courseStatus = new CourseStatus(null, studentCourse.getId(), "仮申込");

        //実行
        sut.registerStudent(studentDetail);

        verify(repository, Mockito.times(1)).registerStudent(student);
        verify(repository, Mockito.times(1)).registerStudentCourse(studentCourse);
        verify(repository, Mockito.times(1)).registerCourseStatus(courseStatus);
    }

    @Test
    void 受講生詳細更新_リポジトリの処理が適切に呼び出せてること() {
        Student student = new Student();
        StudentCourse studentCourse = new StudentCourse();
        List<StudentCourse> studentCoursesList = List.of(studentCourse);
        StudentDetail studentDetail = new StudentDetail(student, studentCoursesList);

        //実行
        sut.updateStudent(studentDetail);

        verify(repository, Mockito.times(1)).updateStudent(student);
        verify(repository, Mockito.times(1)).updateStudentCourse(studentCourse);
    }

    @Test
    void コース申し込み状況更新_リポジトリの処理が適切に呼び出せてること() {
        CourseStatus courseStatus = new CourseStatus();

        sut.updateCourseStatus(courseStatus);

        verify(repository, Mockito.times(1)).updateCourseStatus(courseStatus);
    }
}