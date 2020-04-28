package marmas.arithmetic.entity;

import marmas.arithmetic.model.MathOperationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.isNull;

@Converter
class MathOperationJpaConverter implements AttributeConverter<MathOperationType, String> {
    @Override
    public String convertToDatabaseColumn(MathOperationType operationType) {
        return isNull(operationType) ? null : operationType.toString();
    }

    @Override
    public MathOperationType convertToEntityAttribute(String string) {
        if (string == null) {
            return null;
        }
        try {
            return MathOperationType.valueOf(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
