package raisetech.StudentManagement.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudentRequest {

    private String id;
    private String name;
    private String kanaName;
    private String nickname;
    private String email;
    private String area;
    private String age;
    private String sex;
    private String remark;
    private String isDeleted;
}
