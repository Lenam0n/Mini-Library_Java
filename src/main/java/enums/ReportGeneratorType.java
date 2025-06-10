// Datei: enums/ReportGeneratorType.java
package enums;

import interfaces.IReportGenerator;
import interfaces.generators.BookReportGenerator;
import interfaces.generators.LoanReportGenerator;
import interfaces.generators.MemberReportGenerator;

import java.util.function.Supplier;

import entities.Book;
import entities.Loan;
import entities.Member;

/**
 * Enum, das für jeden Modelltyp (Book, Member, Loan) den passenden
 * IReportGenerator‐Supplier hält. Die createFor(...)‐Methode sucht hier
 * nach dem passenden Eintrag, ohne lange if/switch‐Blöcke.
 */
public enum ReportGeneratorType {
    BOOK(
        Book.class,
        (Supplier<IReportGenerator<?>>) BookReportGenerator::new
    ),
    MEMBER(
        Member.class,
        (Supplier<IReportGenerator<?>>) MemberReportGenerator::new
    ),
    LOAN(
        Loan.class,
        (Supplier<IReportGenerator<?>>) LoanReportGenerator::new
    );

    private final Class<?> modelClass;
    private final Supplier<IReportGenerator<?>> generatorSupplier;

    ReportGeneratorType(Class<?> modelClass, Supplier<IReportGenerator<?>> supplier) {
        this.modelClass = modelClass;
        this.generatorSupplier = supplier;
    }

    /**
     * Liefert den passenden IReportGenerator<T> für den übergebenen Modell‐Typ.
     *
     * @param type Modell‐Klasse (z.B. Book.class, Member.class, Loan.class)
     * @param <T>  Modelltyp
     * @return eine neue Instanz des korrekten Generators
     * @throws IllegalArgumentException, wenn kein Enum‐Eintrag existiert
     */
    @SuppressWarnings("unchecked")
	public static <T> IReportGenerator<T> createFor(Class<T> type) {
        for (ReportGeneratorType entry : values()) {
            if (entry.modelClass.equals(type)) {
                return (IReportGenerator<T>) entry.generatorSupplier.get();
            }
        }
        throw new IllegalArgumentException("Kein ReportGenerator für den Typ: " + type.getName());
    }
}
