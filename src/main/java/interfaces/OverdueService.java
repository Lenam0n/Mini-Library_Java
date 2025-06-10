// Datei: interfaces/OverdueService.java
package interfaces;

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
