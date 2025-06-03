package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Schema(description = "コース申し込み状況")
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseStatus {

    @Pattern(regexp = "\\d+$")
    private String id;

    @Pattern(regexp = "\\d+$")
    private String courseId;

    @NotBlank(message = "申し込み状況を入力してください。")
    private String courseStatus;
}
