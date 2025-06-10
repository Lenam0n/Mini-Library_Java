// Datei: interfaces/OverdueService.java
package services;

import java.time.temporal.ChronoUnit;

import entities.Loan;
import global.Repositories;

/**
 * Interface für Verspätungs-Logik.
 * Enthält Default-Implementierungen aller Methoden.
 */
public interface OverdueService {
    /** Anzahl der Kulanztage */
    long GRACE_DAYS = 30;
    /** Gebühr pro Tag außerhalb der Kulanz */
    double DAY_FEE = 0.5;

    /**
     * Berechnet die Verspätungsgebühr:
     * – Tage ≤ GRACE_DAYS → 0.0
     * – sonst: (daysBorrowed – GRACE_DAYS) * DAY_FEE
     *
     * @param isbn          ISBN des Buchs
     * @param daysBorrowed  Anzahl geliehener Tage
     * @return berechnete Gebühr
     */
    default double calculateFee(String isbn, long daysBorrowed) {
        return (daysBorrowed <= GRACE_DAYS)
            ? 0.0
            : (daysBorrowed - GRACE_DAYS) * DAY_FEE;
    }

    default void demandFee(String isbn) {
        Loan loan = findActiveLoan(isbn);
        long days = ChronoUnit.DAYS.between(
                                        loan.getBorrowDate()
                                        , java.time.LocalDate.now());
        double fee = calculateFee(isbn, days);
        if (fee > 0) { System.out.printf("Buch %s: %,.2f € Mahngebühr (ausgeliehen %d Tage)%n", isbn, fee, days); } 
        else { System.out.printf("Buch %s: keine Gebühr (ausgeliehen %d Tage)%n", isbn, days); }
    }

    /**
     * Sucht den aktiven Loan zum angegebenen ISBN.
     * Gibt null zurück, wenn keine aktive Ausleihe existiert.
     *
     * @param isbn  ISBN des Buchs
     * @return Loan oder null
     */
    default Loan findActiveLoan(String isbn) {
        return Repositories.LOAN.findBy(
            loan -> loan.getIsbn().equals(isbn))
        						  .getValue()
        						  .stream()
        						  .findFirst()
        						  .orElse(null);
    }

}
