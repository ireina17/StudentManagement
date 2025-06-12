package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.dto.request.StudentRequest;
import raisetech.StudentManagement.data.CourseStatus;
import raisetech.StudentManagement.domain.StudentDetail;
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
     * 受講生詳細の検索です。
     * IDに紐づく任意の受講生の情報を取得します。
     *
     * @param id 受講生ID
     * @return 受講生詳細
     */
    @Operation(summary = "受講生検索", description = "受講生のID検索を行います。", method = "/student/{id}")
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable String id) {
        if (!id.matches("\\d+$")) {
            throw new IllegalArgumentException("受講生IDは数字のみ入力するようにしてください。");
        }
        return service.searchStudentById(id);
    }

    /**
     * 受講生詳細の検索です。
     * リスエストパラメータで任意に完全一致する受講生の情報を取得します。
     * 指定がない場合は全ての受講生の情報を取得します。
     *
     * @return 受講生詳細
     */
    @Operation(summary = "受講生検索", description = "受講生の名前を検索します。", method = "/student")
    @GetMapping("/student")
    public List<StudentDetail> getStudentList(@ModelAttribute StudentRequest studentRequest) {
        return service.findStudents(studentRequest);
    }

    /**
     * コース申し込み状況の一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return コース申し込み状況の一覧(全件)
     */
    @Operation(summary = "コース申し込み状況一覧検索", description = "コース申し込み状況の検索を行います。", method = "/courseStatusList")
    @GetMapping("/courseStatus")
    public List<CourseStatus> getCourseStatusList() {
        return service.findCourseStatusByAll();
    }

    /**
     * コース申し込み状況の検索です。
     * IDに紐づく任意のコース申し込み状況の情報を取得します。
     *
     * @param id コース申し込み状況ID
     * @return コース申し込み状況
     */
    @Operation(summary = "コース申し込み状況検索", description = "コース申し込み状況のID検索を行います。")
    @GetMapping("/courseStatus/{id}")
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
    public ResponseEntity<String> updateCourseStatus(@RequestBody @Valid CourseStatus courseStatus) {
        service.updateCourseStatus(courseStatus);
        return ResponseEntity.ok("更新処理が成功しました。");
    }

    @GetMapping("/exception")
    public ResponseEntity<String> throwException() throws UnavailableApiVersionException {
        throw new UnavailableApiVersionException("このAPIは現在使用できません。古いURLとなっています。");
    }
}
