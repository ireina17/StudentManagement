package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable String id) {
        if (!id.matches("\\d+$")) {
            throw new IllegalArgumentException("受講生IDは数字のみ入力するようにしてください。");
        }
        return service.searchStudent(id);
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

    @GetMapping("/exception")
    public ResponseEntity<String> throwException() throws UnavailableApiVersionException {
        throw new UnavailableApiVersionException("このAPIは現在使用できません。古いURLとなっています。");
    }
}
