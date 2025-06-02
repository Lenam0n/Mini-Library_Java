// Datei: interfaces/IReportGenerator.java
package interfaces;

import java.util.List;

/**
 * Generiert Berichte für beliebige Typen T.
 * Default‐Methoden übernehmen das Zusammenfügen von
 * header(), formatItem(...) und footer(...) zu einem vollständigen Report.
 */
public interface IReportGenerator<T> {
    String header();
    String formatItem(T item);
    String footer(int count);

    default String generate(List<T> items) {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, header());
        for (T item : items) {
            appendLine(sb, formatItem(item));
        }
        sb.append(footer(items.size()));
        return sb.toString();
    }

    default void appendLine(StringBuilder sb, String text) {
        sb.append(text)
          .append(System.lineSeparator());
    }

    /**
     * Delegiert an ReportGeneratorType.createFor(...)
     */
    static <T> IReportGenerator<T> createFor(Class<T> type) {
        return enums.ReportGeneratorType.createFor(type);
    }
}
