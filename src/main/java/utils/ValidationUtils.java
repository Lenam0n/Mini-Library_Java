package utils;

import global.Result;
import interfaces.IValidator;

/**
 * Hilfsklasse mit statischer Methode, die einen Validator<T> aufruft.
 */
public class ValidationUtils {

    /**
     * Validiert einen Wert mit einem Validator<T>.
     * 
     * @param value     das zu prüfende Objekt
     * @param validator ein Validator, der Result.success(value) oder Result.error(msg) liefert
     * @param <T>       der Typ des Werts
     * @return Result<T, String> mit entweder dem validen Wert oder einer Fehlermeldung
     */
    public static <T> Result<T, String> validate(
            T value,
            IValidator<T> validator
    ) {
        return validator.validate(value);
    }
}
