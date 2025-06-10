// ---------------------
// Datei: interfaces/Validatable.java
// ---------------------
package utils;

import global.Result;
import interfaces.IValidator;

import java.lang.reflect.Field;

/**
 * Entities, die Validierung unterstützen, implementieren dieses Interface.
 *
 * @param <T> Typ der Entity
 * @param <V> Typ des Validators für T (muss IValidator<T> implementieren)
 */
public interface Validatable<T, V extends IValidator<T>> {
/**
     * Default-Implementation, die per Reflection das statische INSTANCE-Feld
     * der zu Foo passenden FooValidator-Klasse ausliest.
     */
    @SuppressWarnings("unchecked")
    default IValidator<T> getValidator() {
        try {
            // Paket und Name der Entity
            String entityClassName   = this.getClass().getName();                       // z.B. "entities.Book"
            String validatorClassName = entityClassName.replace(
                ".entities.", ".validators."
            ) + "Validator";                                                            // z.B. "validators.BookValidator"

            Class<?> validatorClass = Class.forName(validatorClassName);
            Field instanceField     = validatorClass.getField("INSTANCE");
            return (IValidator<T>) instanceField.get(null);
        } catch (Exception e) {
            throw new IllegalStateException(
                "Validator für " + this.getClass().getSimpleName() +
                " konnte nicht geladen werden", e
            );
        }
    }

    /**
     * Führt die Validierung über den zugeordneten Validator aus.
     *
     * @return Result.success(this) oder Result.error(e)
     */
    @SuppressWarnings("unchecked")
    default Result<T, RuntimeException> validate() {
        return getValidator().validateEntity((T) this);
    }
}
