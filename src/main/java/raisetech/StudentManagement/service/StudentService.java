package raisetech.StudentManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.controller.dto.request.StudentRequest;
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
     * 受講生詳細の一覧検索です。
     * リクエストパラメータの名前、カナ名,ニックネーム、メール、地域、年齢、性別、備考、削除フラグを
     * １つ受け取り条件に応じで完全一致する受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     * リクエストパラメータがない場合は全件検索を行う。
     *
     * @param studentRequest 受講生のリクエストパラメータ
     * @return 受講生詳細
     **/
    public List<StudentDetail> findStudents(StudentRequest studentRequest) {
        List<Student> studentList;

        if (studentRequest.getName() != null) {
            studentList = repository.findStudentsByName(studentRequest.getName());
            return entityConverter.convertStudentDetail(studentList);
        } else if (studentRequest.getKanaName() != null) {
            studentList = repository.findStudentsByKanaName(studentRequest.getKanaName());
            return entityConverter.convertStudentDetail(studentList);
        } else if (studentRequest.getNickname() != null) {
            studentList = repository.findStudentsByNickname(studentRequest.getNickname());
            return entityConverter.convertStudentDetail(studentList);
        } else if (studentRequest.getEmail() != null) {
            studentList = repository.findStudentsByEmail(studentRequest.getEmail());
            return entityConverter.convertStudentDetail(studentList);
        } else if (studentRequest.getArea() != null) {
            studentList = repository.findStudentsByArea(studentRequest.getArea());
            return entityConverter.convertStudentDetail(studentList);
        } else if (studentRequest.getAge() != null) {
            studentList = repository.findStudentsByAge(studentRequest.getAge());
            return entityConverter.convertStudentDetail(studentList);
        } else if (studentRequest.getSex() != null) {
            studentList = repository.findStudentsBySex(studentRequest.getSex());
            return entityConverter.convertStudentDetail(studentList);
        } else if (studentRequest.getRemark() != null) {
            studentList = repository.findStudentsByRemark(studentRequest.getRemark());
            return entityConverter.convertStudentDetail(studentList);
        } else if (studentRequest.getIsDeleted() != null) {
            studentList = repository.findStudentsByIsDeleted(studentRequest.getIsDeleted());
            return entityConverter.convertStudentDetail(studentList);
        } else {
            studentList = repository.findStudentsByAll();
            List<StudentCourse> studentCourseList = repository.findStudentCoursesByAll();
            return converter.convertStudentDetails(studentList, studentCourseList);
        }
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
