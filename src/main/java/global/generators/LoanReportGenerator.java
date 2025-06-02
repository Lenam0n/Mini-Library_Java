// Datei: global/LoanReportGenerator.java
package global.generators;

import interfaces.IReportGenerator;

import java.time.format.DateTimeFormatter;

import entities.Loan;

/**
 * Generiert einen Report für Loan-Objekte aus dem global-Package.
 * Nutzt die Default‐generate(List<T>) im Interface.
 */
public class LoanReportGenerator implements IReportGenerator<Loan> {

    private static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LoanReportGenerator() { }

    @Override
    public String header() {
        return ""
            + "=== Ausleihreport ===\n"
            + "ISBN           | Member ID | Ausleihdatum\n"
            + "-----------------------------------------";
    }

    @Override
    public String formatItem(Loan item) {
        return String.format(
            "%-14s | %9d | %s",
            item.getIsbn(),
            item.getMemberId(),
            item.getBorrowDate().format(DATE_FORMAT)
        );
    }

    @Override
    public String footer(int count) {
        return String.format("Anzahl Ausleihen: %d", count);
    }
}
