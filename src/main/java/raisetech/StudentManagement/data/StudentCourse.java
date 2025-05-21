package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudentCourse {

    @Pattern(regexp = "\\d+$")
    private String id;

    @Pattern(regexp = "\\d+$")
    private String studentId;

    @NotBlank
    private String courseName;

    private LocalDateTime courseStart;
    private LocalDateTime courseEnd;
}
