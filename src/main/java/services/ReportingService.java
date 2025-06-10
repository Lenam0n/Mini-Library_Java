package services;

import java.util.List;

import entities.Book;
import entities.Loan;
import entities.Member;
import exception.ReportNullException;
import interfaces.IReportGenerator;
import global.Result;

/**
 * Interface für Report-Erzeugungen und -Ausgaben.
 * Erweitert PrintService und nutzt dessen Methoden zum Drucken.
 */
public interface ReportingService extends PrintService {

    /**
     * Druckt einen Ausleihreport.
     *
     * @param loans Liste aller Ausleihen
     */
    default void printLoanReport(List<Loan> loans) {
        IReportGenerator<Loan> generator = IReportGenerator.createFor(Loan.class);
        printHeader("=== Ausleihreport ===");
        String report = generator.generate(loans);
        if (report != null) {
            print(Result.success(report));
        } else {
            print(Result.error(new ReportNullException("Ausleihreport")));
        }
        printFooter();
    }

    /**
     * Druckt einen Bücherreport.
     *
     * @param books Liste aller Bücher
     */
    default void printBookReport(List<Book> books) {
        IReportGenerator<Book> generator = IReportGenerator.createFor(Book.class);
        printHeader("=== Bücherreport ===");
        String report = generator.generate(books);
        if (report != null) {
            print(Result.success(report));
        } else {
            print(Result.error(new ReportNullException("Bücherreport")));
        }
        printFooter();
    }

    /**
     * Druckt einen Mitgliederreport.
     *
     * @param members Liste aller Mitglieder
     */
    default void printMemberReport(List<Member> members) {
        IReportGenerator<Member> generator = IReportGenerator.createFor(Member.class);
        printHeader("=== Mitgliederreport ===");
        String report = generator.generate(members);
        if (report != null) {
            print(Result.success(report));
        } else {
            print(Result.error(new ReportNullException("Mitgliederreport")));
        }
        printFooter();
    }
}
