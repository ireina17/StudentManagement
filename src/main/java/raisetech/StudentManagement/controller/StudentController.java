package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.CourseStatusNotAllowedException;
import raisetech.StudentManagement.exception.UnavailableApiVersionException;
import raisetech.StudentManagement.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return 受講生詳細の一覧(全件)
     */
    @Operation(summary = "受講生一覧検索", description = "受講生の検索を行います。", method = "/studentList")
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

    /**
     * 受講生詳細の検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生詳細
     */
    @Operation(summary = "受講生検索", description = "受講生のID検索を行います。")
    @GetMapping("/student/id={id}")
    public StudentDetail getStudent(@PathVariable String id) {
        if (!id.matches("\\d+$")) {
            throw new IllegalArgumentException("受講生IDは数字のみ入力するようにしてください。");
        }
        return service.searchStudentId(id);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致する名前の受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生名前検索", description = "受講生の名前を検索します。", method = "/student/name={name}")
    @GetMapping("/student/name={name}")
    public List<StudentDetail> getStudentName(@PathVariable String name) {
        return service.searchStudentName(name);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致するカナ名の受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生カナ名検索", description = "受講生のカナ名を検索します。", method = "/student/kanaName={kanaName}")
    @GetMapping("/student/kanaName={kanaName}")
    public List<StudentDetail> getStudentKanaName(@PathVariable String kanaName) {
        return service.searchStudentKanaName(kanaName);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致するニックネームの受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生ニックネーム検索", description = "受講生のニックネームを検索します。", method = "/student/nickname={nickname}")
    @GetMapping("/student/nickname={nickname}")
    public List<StudentDetail> getStudentNickname(@PathVariable String nickname) {
        return service.searchStudentNickname(nickname);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致するメールの受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生メール検索", description = "受講生のメールを検索します。", method = "/student/email={email}")
    @GetMapping("/student/email={email}")
    public List<StudentDetail> getStudentEmail(@PathVariable String email) {
        return service.searchStudentEmail(email);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致する地域の受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生地域検索", description = "受講生の地域を検索します。", method = "/student/area={area}")
    @GetMapping("/student/area={area}")
    public List<StudentDetail> getStudentArea(@PathVariable String area) {
        return service.searchStudentArea(area);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致する年齢の受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生年齢検索", description = "受講生の年齢を検索します。", method = "/student/age={age}")
    @GetMapping("/student/age={age}")
    public List<StudentDetail> getStudentAge(@PathVariable String age) {
        return service.searchStudentAge(age);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致する性別の受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生性別検索", description = "受講生の性別を検索します。", method = "/student/sex={sex}")
    @GetMapping("/student/sex={sex}")
    public List<StudentDetail> getStudentSex(@PathVariable String sex) {
        return service.searchStudentSex(sex);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致する備考の受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生備考検索", description = "受講生の備考を検索します。", method = "/student/remark={remark}")
    @GetMapping("/student/remark={remark}")
    public List<StudentDetail> getStudentRemark(@PathVariable String remark) {
        return service.searchStudentRemark(remark);
    }

    /**
     * 受講生詳細の検索です。
     * 任意に完全一致する削除フラグの受講生の情報を取得します。(複数の場合あり)
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生削除フラグ検索", description = "受講生の削除フラグを検索します。", method = "/student/isDeleted={isDeleted}")
    @GetMapping("/student/isDeleted={isDeleted}")
    public List<StudentDetail> getStudentIsDeleted(@PathVariable String isDeleted) {
        return service.searchStudentIsDeleted(isDeleted);
    }

    /**
     * コース申し込み状況の一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return コース申し込み状況の一覧(全件)
     */
    @Operation(summary = "コース申し込み状況一覧検索", description = "コース申し込み状況の検索を行います。", method = "/courseStatusList")
    @GetMapping("/courseStatusList")
    public List<CourseStatus> getCourseStatusList() {
        return service.searchCourseStatusList();
    }

    /**
     * コース申し込み状況の検索です。
     * IDに紐づく任意のコース申し込み状況の情報を取得します。
     *
     * @param id コース申し込み状況ID
     * @return コース申し込み状況
     */
    @Operation(summary = "コース申し込み状況検索", description = "コース申し込み状況のID検索を行います。")
    @GetMapping("/courseStatus/id={id}")
    public CourseStatus getCourseStatus(@PathVariable String id) {
        if (!id.matches("\\d+$")) {
            throw new IllegalArgumentException("コース申し込み状況IDは数字のみ入力するようにしてください。");
        }
        return service.searchCourseStatus(id);
    }

    /**
     * 受講生の登録を行います。
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @Operation(summary = "受講生登録", description = "受講生の登録を行います。")
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。
     * キャンセルフラグの更新もここで行います(論理削除)
     *
     * @param studentDetail 受講生詳細
     * @return 実行結果
     */
    @Operation(summary = "受講生更新", description = "受講生の更新を行います。")
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました。");
    }

    /**
     * コース申し込み状況の更新を行います。
     *
     * @param courseStatus コース申し込み状況
     * @return 実行結果
     */
    @Operation(summary = "コース申し込み状況", description = "コース申し込み状況の更新を行います。")
    @PutMapping("/updateCourseStatus")
    public ResponseEntity<String> updateCourseStatus(@RequestBody @Valid CourseStatus courseStatus) throws CourseStatusNotAllowedException {
        String status = courseStatus.getCourseStatus();
        if (!status.equals("仮申込") &&
                !status.equals("本申込") &&
                !status.equals("受講中") &&
                !status.equals("受講終了")) {
            throw new CourseStatusNotAllowedException();
        }
        service.updateCourseStatus(courseStatus);
        return ResponseEntity.ok("更新処理が成功しました。");
    }

    @GetMapping("/exception")
    public ResponseEntity<String> throwException() throws UnavailableApiVersionException {
        throw new UnavailableApiVersionException("このAPIは現在使用できません。古いURLとなっています。");
    }
}
