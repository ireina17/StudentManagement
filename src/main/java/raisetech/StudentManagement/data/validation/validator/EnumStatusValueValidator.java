package raisetech.StudentManagement.data.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import raisetech.StudentManagement.data.validation.annotation.EnumStatusValue;

import java.lang.reflect.Method;

public class EnumStatusValueValidator implements ConstraintValidator<EnumStatusValue, String> {

    private Enum<?>[] enumConstants;

    @Override
    public void initialize(EnumStatusValue annotation) {
        Class<? extends Enum<?>> enumClass = annotation.enumClass();
        enumConstants = enumClass.getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (Enum<?> e : enumConstants) {
            try {
                Method getLabel = e.getClass().getMethod("getLabel");
                String label = (String) getLabel.invoke(e);
                if (label.equals(value)) {
                    return true;
                }
            } catch (Exception ex) {
                // getLabelがなければ無視します。
            }
        }
        return false;

    }
}
