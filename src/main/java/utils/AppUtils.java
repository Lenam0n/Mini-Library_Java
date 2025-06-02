package utils;

import java.util.List;

import interfaces.IReportGenerator;

/**
 * Hilfsmethoden zum Ausgeben von Listen und Reports.
 */
public class AppUtils {
    private AppUtils() { /* Utility-Klasse darf nicht instanziiert werden */ }

    public static <T> void printList(String title, List<T> items) {
        System.out.println(title);
        for (T item : items) {
            System.out.println(item);
        }
        System.out.println();
    }

    public static <T> void printReport(String title,
                                       IReportGenerator<T> generator,
                                       List<T> items) {
        System.out.println("=== " + title + " ===");
        System.out.println(generator.generate(items));
        System.out.println();
    }
}
