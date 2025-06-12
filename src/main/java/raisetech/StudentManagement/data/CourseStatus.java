package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import raisetech.StudentManagement.data.model.Status;
import raisetech.StudentManagement.data.validation.annotation.EnumStatusValue;

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

    @NotNull
    @EnumStatusValue(enumClass = Status.class, message = "入力されたコースステータスは許可されていません。仮申込／本申込／受講中／受講終了を入力してください。")
    private String courseStatus;
}
