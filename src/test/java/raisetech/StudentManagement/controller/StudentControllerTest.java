package raisetech.StudentManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).searchStudentList();
    }

    @Test
    void 受講生詳細の受講生ID検索が実行できて空のリストが返ってくること() throws Exception {
        String id = "999";
        StudentDetail studentDetail = new StudentDetail();
        when(service.searchStudent(id)).thenReturn(new StudentDetail());

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
                .andExpect(status().isOk());

        verify(service, Mockito.times(1)).searchStudent(id);
    }


    //数字意外だった場合
    @Test
    void 受講生詳細の受講生ID検索が数字以外入力時に異常が発生すること() throws Exception {
        String id = "テストです。";
        StudentDetail studentDetail = new StudentDetail();

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("受講生IDは数字のみ入力するようにしてください。"));
    }

    @Test
    void 受講生詳細の受講生登録が実行できて空が返ってくること() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                """
                                        {
                                            "student":{
                                                "name" : "田中太郎",
                                                "kanaName" : "タナカタロウ",
                                                "nickname" : "タロウ",
                                                "email" : "test@example.com",
                                                "area" : "東京",
                                                "age" : "20",
                                                "sex" : "男性",
                                                "remark" : ""
                                            },
                                            "studentCourseList" : [
                                                {
                                                    "courseName" : "Javaコース"
                                                }
                                            ]
                                        }
                                        """
                        ))
                .andExpect(status().isOk());
        verify(service, Mockito.times(1)).registerStudent(any());
    }

    @Test
    void 受講生詳細の受講生更新が実行できること() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                """
                                        {
                                            "student": {
                                                "id": "1",
                                                "name": "田中太郎",
                                                "kanaName": "タナカタロウ",
                                                "nickname": "タロウ",
                                                "email": "test@example.com",
                                                "area": "東京",
                                                "age": 20,
                                                "sex": "男性",
                                                "remark": "",
                                                "deleted": false
                                            },
                                            "studentCourseList": [
                                                {
                                                    "id": "1",
                                                    "studentId": "1",
                                                    "courseName": "AWSコース",
                                                    "courseStart": "2025-04-01T00:00:00",
                                                    "courseEnd": "2026-04-01T00:00:00"
                                                }
                                            ]
                                        }
                                        """
                        ))
                .andExpect(status().isOk());
        verify(service, Mockito.times(1)).updateStudent(any());
    }

    @Test
    void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/exception"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("このAPIは現在使用できません。古いURLとなっています。"));
    }

    @Test
    void 受講生詳細の受講生で適切な値を入力時に入力チェックに異常が発生しないこと() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("test@example.com");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void 受講生詳細の受講生でIDに数値以外を用いた時に入力チェックに掛かること() {
        Student student = new Student();
        student.setId("テストです。");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("test@example.com");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("数字のみ入力するようにしてください。");
    }

    @Test
    void 受講生詳細の受講生で名前が入力時に入力チェックに異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("test@example.com");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("名前を入力してください。");
    }

    @Test
    void 受講生詳細の受講生でカナ名が入力時に入力チェックに異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("");
        student.setNickname("タロウ");
        student.setEmail("test@example.com");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("カナ名を入力してください。");
    }

    @Test
    void 受講生詳細の受講生でニックネームが入力時に入力チェックに異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("");
        student.setEmail("test@example.com");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("ニックネームを入力してください。");
    }

    @Test
    void 受講生詳細の受講生でメールが入力時に入力チェックに異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(2);
        assertThat(violations).extracting("message")
                .containsOnly("メールアドレスを入力してください。");
    }

    @Test
    void 受講生詳細の受講生でメールが入力時にアットマークがない場合で異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("testexample");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("メールアドレスを入力してください。");

    }

    @Test
    void 受講生詳細の受講生でメールが入力時にトップレベルドメインがない場合で異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("test@example");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("メールアドレスを入力してください。");
    }

    @Test
    void 受講生詳細の受講生でメールが入力時にユーザー名がない場合で異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("@example.com");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("メールアドレスを入力してください。");
    }

    @Test
    void 受講生詳細の受講生でメールが入力時にドメインの形式が不正の場合で異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("test@.com");
        student.setArea("東京都");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("メールアドレスを入力してください。");
    }

    @Test
    void 受講生詳細の受講生で地域が入力時に入力チェックに異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("test@example.com");
        student.setArea("");
        student.setSex("男性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("住んでいる地域を入力してください。");
    }

    @Test
    void 受講生詳細の受講生で性別が入力時に入力チェックに異常が発生すること() {
        Student student = new Student();
        student.setId("999");
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("test@example.com");
        student.setArea("東京都");
        student.setSex("");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("性別を入力してください。");
    }

    @Test
    void 受講生コース情報で適切な値を入力時に入力チェックに異常が発生しないこと() {
        StudentCourse studentCourse = new StudentCourse();
        LocalDateTime now = LocalDateTime.now();
        studentCourse.setId("999");
        studentCourse.setStudentId("999");
        studentCourse.setCourseName("JAVAコース");
        studentCourse.setCourseStart(now);
        studentCourse.setCourseEnd(now.plusYears(1));

        Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void 受講生コース情報でIDに数値以外を用いた時に入力チェックに掛かること() {
        StudentCourse studentCourse = new StudentCourse();
        LocalDateTime now = LocalDateTime.now();
        studentCourse.setId("テストです。");
        studentCourse.setStudentId("999");
        studentCourse.setCourseName("JAVAコース");
        studentCourse.setCourseStart(now);
        studentCourse.setCourseEnd(now.plusYears(1));

        Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void 受講生コース情報でStudentIDに数値以外を用いた時に入力チェックに掛かること() {
        StudentCourse studentCourse = new StudentCourse();
        LocalDateTime now = LocalDateTime.now();
        studentCourse.setId("999");
        studentCourse.setStudentId("テストです。");
        studentCourse.setCourseName("JAVAコース");
        studentCourse.setCourseStart(now);
        studentCourse.setCourseEnd(now.plusYears(1));

        Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void 受講生コース情報でコース情報が入力されていない時に入力チェックに掛かること() {
        StudentCourse studentCourse = new StudentCourse();
        LocalDateTime now = LocalDateTime.now();
        studentCourse.setId("999");
        studentCourse.setStudentId("999");
        studentCourse.setCourseName("");
        studentCourse.setCourseStart(now);
        studentCourse.setCourseEnd(now.plusYears(1));

        Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);
        assertThat(violations.size()).isEqualTo(1);
    }

}