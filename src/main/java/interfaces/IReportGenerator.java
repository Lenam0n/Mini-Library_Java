package interfaces;

import java.util.List;

public interface IReportGenerator<T> {
    /**
     * Liefert den statischen Kopf deines Reports, z. B. 
     * – einen Titel oder eine Überschrift
     * – Spaltennamen
     * – eine Trennlinie
     *
     * Beispiel für Bücher:
     *   "=== Bücherliste ==="
     *   "ISBN           | Titel               | Autor"
     *   "-------------------------------------------"
     *
     * @return String, der vor allen Items steht
     */
    String header();

    /**
     * Formatiert genau ein Item vom Typ T als eine Text-Zeile.
     * 
     * Aufgabe:
     * – Extrahiere die relevanten Felder aus dem Item
     * – Baue einen gut lesbaren String zusammen
     * – Achte auf einheitliche Längen oder Trennzeichen, falls nötig
     *
     * Beispiel für ein Book-Objekt:
     *   formatItem(book) → "978-0132350884 | Clean Code      | Robert C. Martin"
     *
     * @param item Das aktuelle Objekt aus der Liste
     * @return formatierte Ein-Zeilen-Darstellung von item
     */
    String formatItem(T item);

    /**
     * Erzeugt den Abschluss deines Reports, oft eine Zusammenfassung.
     * 
     * Typische Inhalte:
     * – "Anzahl Objekte: X"
     * – Datum/Uhrzeit der Generierung
     * – Trennlinie am Ende
     *
     * Beispiel:
     *   footer(3) → "Anzahl Bücher: 3"
     *
     * @param count Anzahl der Items, die verarbeitet wurden
     * @return String, der unter allen Items steht
     */
    String footer(int count);

    /**
     * Default-Implementierung, die:
     * 1. header() am Anfang anhängt
     * 2. für jedes Element in items formatItem(item) aufruft und 
     *    das Ergebnis jeweils in eine neue Zeile schreibt
     * 3. footer(items.size()) am Ende anhängt
     *
     * Hinweise zur Implementierung:
     * – Benutze einen StringBuilder für Effizienz
     * – Erzeuge zwischen den Blöcken (Header, Items, Footer) jeweils einen
     *   Zeilenumbruch („\n“ oder System.lineSeparator())
     * – Sorge dafür, dass die Ausgabe schön gegliedert ist
     *
     * So lieferst du einen vollständigen Report-String zurück.
     */
    default String generate(List<T> items) {
        StringBuilder sb = new StringBuilder();
        appendLine(sb, header());
        for (T item : items) appendLine(sb, formatItem(item));
        
        sb.append(footer(items.size()));
        return sb.toString();
    }

    default void appendLine(StringBuilder sb, String text) {
        sb.append(text)
          .append(System.lineSeparator());
    }
}
