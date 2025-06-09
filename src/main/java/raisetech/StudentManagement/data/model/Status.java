package raisetech.StudentManagement.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    PROVISIONAL_APPLICATION("仮申込"),
    ACTUAL_APPLICATION("本申込"),
    CURRENTLY_REGISTERING("受講中"),
    REGISTRATION_COMPLETED("受講終了");

    private String label;

}
