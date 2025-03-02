package br.com.fiap.webui.validations.validators;

import br.com.fiap.webui.validations.ValueOfEnum;
import br.com.fiap.utils.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.ArrayUtils.contains;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private List<String> acceptedValues;

    @Override
    public void initialize(final ValueOfEnum annonation) {
        this.acceptedValues = Stream.of(annonation.enumClass().getEnumConstants())
                .map(Enum::name)
                .filter(value -> !contains(annonation.exclusions(), value))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Strings.isEmpty(value) || this.acceptedValues.contains(value);
    }
}
