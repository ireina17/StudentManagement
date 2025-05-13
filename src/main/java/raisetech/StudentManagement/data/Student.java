package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Schema(description = "受講生")
@Getter
@Setter
@Validated
public class Student {

    @Pattern(regexp = "\\d+$", message = "数字のみ入力するようにしてください。")
    private String id;

    @NotBlank(message = "名前を入力してください。")
    private String name;

    @NotBlank(message = "カナ名を入力してください。")
    private String kanaName;

    @NotBlank(message = "ニックネームを入力してください。")
    private String nickname;

    @NotBlank(message = "メールアドレスを入力してください。")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "メールアドレスを入力してください。")
    private String email;

    @NotBlank(message = "住んでいる地域を入力してください。")
    private String area;

    private int age;

    @NotBlank(message = "性別を入力してください。")
    private String sex;

    private String remark;
    private boolean isDeleted;
}
