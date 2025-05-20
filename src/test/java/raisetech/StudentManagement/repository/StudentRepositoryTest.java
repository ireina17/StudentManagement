package raisetech.StudentManagement.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行えること() {
        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(5);
    }

    @Test
    void 受講生の登録が行えること() {
        Student student = new Student();
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("test@example.com");
        student.setArea("東京都");
        student.setAge(25);
        student.setSex("男性");
        student.setRemark("");

        sut.registerStudent(student);
        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(6);
    }
}