package raisetech.StudentManagement.controller;

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
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

import java.time.LocalDateTime;
import java.util.List;
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

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByAll();
    }

    @Test
    void 受講生詳細の受講生ID検索が実行できて空のリストが返ってくること() throws Exception {
        String id = "999";
        StudentDetail studentDetail = new StudentDetail();
        when(service.searchStudentById(id)).thenReturn(new StudentDetail());

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
                .andExpect(status().isOk());

        verify(service, Mockito.times(1)).searchStudentById(id);
    }

    @Test
    void 受講生詳細の受講生ID検索が数字以外入力時に異常が発生すること() throws Exception {
        String id = "テストです。";
        StudentDetail studentDetail = new StudentDetail();

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("受講生IDは数字のみ入力するようにしてください。"));
    }

    @Test
    void 受講生詳細の受講生名前検索が実行できて空のリストが返ってくること() throws Exception {
        String name = "田中太郎";
        when(service.findStudentsByName(name)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?name={name}", name))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByName(name);
    }

    @Test
    void 受講生詳細の受講生カナ名検索が実行できて空のリストが返ってくること() throws Exception {
        String kanaName = "タナカタロウ";
        when(service.findStudentsByKanaName(kanaName)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?kanaName={kanaName}", kanaName))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByKanaName(kanaName);
    }

    @Test
    void 受講生詳細の受講生ニックネーム検索が実行できて空のリストが返ってくること() throws Exception {
        String nickname = "タロウ";
        when(service.findStudentsByNickname(nickname)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?nickname={nickname}", nickname))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByNickname(nickname);
    }

    @Test
    void 受講生詳細の受講生メール検索が実行できて空のリストが返ってくること() throws Exception {
        String email = "test@example.com";
        when(service.findStudentsByEmail(email)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?email={email}", email))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByEmail(email);
    }

    @Test
    void 受講生詳細の受講生地域検索が実行できて空のリストが返ってくること() throws Exception {
        String area = "東京都";
        when(service.findStudentsByArea(area)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?area={area}", area))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByArea(area);
    }

    @Test
    void 受講生詳細の受講生年齢検索が実行できて空のリストが返ってくること() throws Exception {
        String age = "20";
        when(service.findStudentsByAge(age)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?age={age}", age))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByAge(age);
    }

    @Test
    void 受講生詳細の受講生性別検索が実行できて空のリストが返ってくること() throws Exception {
        String sex = "男性";
        when(service.findStudentsBySex(sex)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?sex={sex}", sex))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsBySex(sex);
    }

    @Test
    void 受講生詳細の受講生備考検索が実行できて空のリストが返ってくること() throws Exception {
        String remark = "テストです。";
        when(service.findStudentsByRemark(remark)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?remark={remark}", remark))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByRemark(remark);
    }

    @Test
    void 受講生詳細の受講生削除フラグ検索が実行できて空のリストが返ってくること() throws Exception {
        String isDeleted = "true";
        when(service.findStudentsByDeleted(isDeleted)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/student?isDeleted={isDeleted}", isDeleted))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findStudentsByDeleted(isDeleted);
    }

    @Test
    void コース申し込み状況の検索が実行できること() throws Exception {
        String id = "999";
        CourseStatus courseStatus = new CourseStatus();
        when((service.searchCourseStatus(id))).thenReturn(courseStatus);

        mockMvc.perform(MockMvcRequestBuilders.get("/courseStatus/{id}", id))
                .andExpect(status().isOk());

        verify(service, Mockito.times(1)).searchCourseStatus(id);
    }

    @Test
    void コース申し込み状況の一覧検索が実行できて空のリストが返ってくること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courseStatus"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(service, Mockito.times(1)).findCourseStatusByAll();
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
    void 申し込み状況の更新が実行できて空が返ってくること() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/updateCourseStatus")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                """
                                                    {
                                                    "id" : "1",
                                                    "courseId" : "1",
                                                    "courseStatus" : "本申込"
                                                }
                                        """
                        ))
                .andExpect(status().isOk());
        verify(service, Mockito.times(1)).updateCourseStatus(any());
    }

    @Test
    void 申し込み状況の例外APIが実行できてステータスが400で返ってくること() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/updateCourseStatus")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                """
                                                    {
                                                    "id" : "1",
                                                    "courseId" : "1",
                                                    "courseStatus" : "テストです。"
                                                }
                                        """
                        ))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("[入力されたコースステータスは許可されていません。仮申込／本申込／受講中／受講終了を入力してください。]"));
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

    @Test
    void コース申し込み状況で適切な値を入力時に入力チェックに異常が発生しないこと() {
        CourseStatus courseStatus = new CourseStatus("999", "999", "仮申込");

        Set<ConstraintViolation<CourseStatus>> violations = validator.validate(courseStatus);
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void コース申し込み状況でidが入力時に入力チェックに異常が発生すること() {
        CourseStatus courseStatus = new CourseStatus("", "999", "仮申込");

        Set<ConstraintViolation<CourseStatus>> violations = validator.validate(courseStatus);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void コース申し込み状況でidが入力時に数値以外を入力チェックに異常が発生すること() {
        CourseStatus courseStatus = new CourseStatus("テストです。", "999", "仮申込");

        Set<ConstraintViolation<CourseStatus>> violations = validator.validate(courseStatus);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void コース申し込み状況でcourseIdが入力時に入力チェックに異常が発生すること() {
        CourseStatus courseStatus = new CourseStatus("999", "", "仮申込");

        Set<ConstraintViolation<CourseStatus>> violations = validator.validate(courseStatus);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void コース申し込み状況でcourseIdが数値以外を入力時に入力チェックに異常が発生すること() {
        CourseStatus courseStatus = new CourseStatus("999", "テストです。", "仮申込");

        Set<ConstraintViolation<CourseStatus>> violations = validator.validate(courseStatus);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void コース申し込み状況で申し込み状況が入力時に入力チェックに異常が発生すること() {
        CourseStatus courseStatus = new CourseStatus("999", "999", "");

        Set<ConstraintViolation<CourseStatus>> violations = validator.validate(courseStatus);
        assertThat(violations.size()).isEqualTo(1);
    }
}