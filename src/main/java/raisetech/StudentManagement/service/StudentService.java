package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.StudentNotFoundException;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.converter.StudentEntityConverter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです。
 * 受験生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

    private StudentRepository repository;
    private StudentConverter converter;
    private StudentEntityConverter entityConverter;

    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter, StudentEntityConverter entityConverter) {
        this.repository = repository;
        this.converter = converter;
        this.entityConverter = entityConverter;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return 受講生詳細一覧(全件)
     */
    public List<StudentDetail> findStudentsByAll() {
        List<Student> studentList = repository.findStudentsByAll();
        List<StudentCourse> studentCourseList = repository.findStudentCoursesByAll();
        return converter.convertStudentDetails(studentList, studentCourseList);
    }

    /**
     * 受講生検索です。
     * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param id 受講生ID
     * @return 受講生詳細
     */
    public StudentDetail searchStudentById(String id) {
        Student student = repository.searchStudentById(id);
        if (student == null) {
            throw new StudentNotFoundException(id);
        }
        List<StudentCourse> studentCourse = repository.searchStudentCourseById(student.getId());
        return new StudentDetail(student, studentCourse);
    }

    /**
     * 受講生検索です。
     * 完全一致するnameの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param name 受講生のname
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsByName(String name) {
        List<Student> studentList = repository.findStudentsByName(name);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * 受講生検索です。
     * 完全一致するkanaNameの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param kanaName 受講生のkanaName
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsByKanaName(String kanaName) {
        List<Student> studentList = repository.findStudentsByKanaName(kanaName);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * 受講生検索です。
     * 完全一致するnicknameの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param nickname 受講生のnickname
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsByNickname(String nickname) {
        List<Student> studentList = repository.findStudentsByNickname(nickname);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * 受講生検索です。
     * 完全一致するemailの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param email 受講生のemail
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsByEmail(String email) {
        List<Student> studentList = repository.findStudentsByEmail(email);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * 受講生検索です。
     * 完全一致するareaの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param area 受講生のarea
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsByArea(String area) {
        List<Student> studentList = repository.findStudentsByArea(area);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * 受講生検索です。
     * 完全一致するageの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param age 受講生のage
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsByAge(String age) {
        List<Student> studentList = repository.findStudentsByAge(age);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * 受講生検索です。
     * 完全一致するsexの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param sex 受講生のsex
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsBySex(String sex) {
        List<Student> studentList = repository.findStudentsBySex(sex);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * 受講生検索です。
     * 完全一致するremarkの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param remark 受講生のremark
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsByRemark(String remark) {
        List<Student> studentList = repository.findStudentsByRemark(remark);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * 受講生検索です。
     * 完全一致するisDeletedの講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param isDeleted 受講生のisDeleted
     * @return 受講生詳細
     */
    public List<StudentDetail> findStudentsByDeleted(String isDeleted) {
        List<Student> studentList = repository.findStudentsByIsDeleted(isDeleted);
        return entityConverter.convertStudentDetail(studentList);
    }

    /**
     * コース申し込み状況の一覧検索です。
     *
     * @return コース申し込み状況
     */
    public CourseStatus searchCourseStatus(String id) {
        return repository.searchCourseStatus(id);
    }

    /**
     * コース申し込み状況の検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return コース申し込み状況一覧(全件)
     */
    public List<CourseStatus> findCourseStatusByAll() {
        return repository.findCourseStatusByAll();
    }

    /**
     * 受講生詳細の登録を行います。
     * 受講生と受講生コース情報とコース申し込み状況を個別に登録します。
     * 受講生コース情報には受講生情報を紐づける値やコース開始日、コース終了日を設定します。
     * コース申し込み状況には受講生コース情報に紐づくidの登録やコース申し込みを設定します。
     *
     * @param studentDetail 受講生詳細
     * @return 登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        repository.registerStudent(student);
        studentDetail.getStudentCourseList().forEach(studentCourse -> {
            initStudentsCourse(studentCourse, student);
            repository.registerStudentCourse(studentCourse);
            CourseStatus courseStatus = new CourseStatus(null, studentCourse.getId(), "仮申込");
            repository.registerCourseStatus(courseStatus);
        });
        return studentDetail;
    }

    /**
     * 受講生コース情報を登録する際の初期情報を設定する。
     *
     * @param studentCourse 受講生コース情報
     * @param student       受講生
     */
    private void initStudentsCourse(StudentCourse studentCourse, Student student) {
        LocalDateTime now = LocalDateTime.now();

        studentCourse.setStudentId(student.getId());
        studentCourse.setCourseStart(now);
        studentCourse.setCourseEnd(now.plusYears(1));
    }

    /**
     * 受講生詳細の更新を行います。
     * 受講生と受講生コース情報をそれぞれ更新します。
     *
     * @param studentDetail 受講生詳細
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        studentDetail.getStudentCourseList()
                .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
    }

    /**
     * コース申し込み状況の更新を行います。
     *
     * @param courseStatus コース申し込み状況
     */
    @Transactional
    public void updateCourseStatus(CourseStatus courseStatus) {
        repository.updateCourseStatus(courseStatus);
    }

}
