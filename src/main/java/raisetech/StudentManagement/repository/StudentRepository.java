package raisetech.StudentManagement.repository;

import org.apache.ibatis.annotations.Mapper;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.util.List;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 * <p>
 * 全件検索や
 */
@Mapper
public interface StudentRepository {

    /**
     * 受講生の全件検索を行います。
     *
     * @return 受講生一覧(全件)
     */
    List<Student> search();

    /**
     * 受講生のid検索を行います。
     *
     * @param id 受講生ID
     * @return 受講生
     */
    Student searchStudentId(String id);

    /**
     * 受講生の名前検索を行います。
     *
     * @param name 受講生のName
     * @return 受講生
     */
    List<Student> searchStudentName(String name);

    /**
     * 受講生のカナ名検索を行います。
     *
     * @param kanaName 受講生のKanaName
     * @return 受講生
     */
    List<Student> searchStudentKanaName(String kanaName);

    /**
     * 受講生のニックネーム検索を行います。
     *
     * @param nickname 受講生のnickname
     * @return 受講生
     */
    List<Student> searchStudentNickname(String nickname);

    /**
     * 受講生のメール検索を行います。
     *
     * @param email 受講生のemail
     * @return 受講生
     */
    List<Student> searchStudentEmail(String email);

    /**
     * 受講生の地域検索を行います。
     *
     * @param area 受講生のarea
     * @return 受講生
     */
    List<Student> searchStudentArea(String area);

    /**
     * 受講生の年齢検索を行います。
     *
     * @param age 受講生のage
     * @return 受講生
     */
    List<Student> searchStudentAge(String age);

    /**
     * 受講生の性別検索を行います。
     *
     * @param sex 受講生のsex
     * @return 受講生
     */
    List<Student> searchStudentSex(String sex);

    /**
     * 受講生の備考検索を行います。
     *
     * @param remark 受講生のremark
     * @return 受講生
     */
    List<Student> searchStudentRemark(String remark);

    /**
     * 受講生の削除フラグ検索を行います。
     *
     * @param isDeleted 受講生のisDeleted
     * @return 受講生
     */
    List<Student> searchStudentIsDeleted(String isDeleted);

    /**
     * 受講生のコース情報の全件検索を行います。
     *
     * @return 受講生のコース情報(全件)
     */
    List<StudentCourse> searchStudentCourseList();

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     *
     * @param studentId 受講生ID
     * @return 受講生IDに紐づく受講生コース情報
     */
    List<StudentCourse> searchStudentCourse(String studentId);

    /**
     * コース申し込み状況の全件検索します。
     *
     * @return コース申し込み状況(全件)
     */
    List<CourseStatus> searchCourseStatusList();

    /**
     * コース申し込み状況IDに紐づくコース申し込み状況の検索します。
     *
     * @param id 　コース申し込み状況ID
     * @return コース申し込み状況IDに紐づくコース申し込み状況
     */
    CourseStatus searchCourseStatus(String id);

    /**
     * 受講生を新規登録します。
     * IDに関しては自動採番を行う。
     *
     * @param student 受講生
     */
    void registerStudent(Student student);

    /**
     * 受講生を新規登録します。
     * IDに関しては自動採番を行う。
     *
     * @param studentCourse 受講生コース情報
     */
    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * コース申し込み状況を新規登録します。
     * IDに関しては自動採番を行う。
     *
     * @param courseStatus 受講生コースID
     */
    void registerCourseStatus(CourseStatus courseStatus);

    /**
     * 受講生を更新します。
     *
     * @param student 受講生
     */
    void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名を更新します。
     *
     * @param studentCourse 受講生コース情報
     */
    void updateStudentCourse(StudentCourse studentCourse);

    /**
     * コース申し込み状況を更新します。
     *
     * @param courseStatus コース申し込み状況
     */
    void updateCourseStatus(CourseStatus courseStatus);
}
