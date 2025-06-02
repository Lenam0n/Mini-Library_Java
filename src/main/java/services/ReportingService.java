package services;

import interfaces.IReportGenerator;
import interfaces.IPrinter;

import java.util.List;

import entities.Book;
import entities.Loan;
import entities.Member;

/**
* Kapselt alle Report-Erzeugungen und -Ausgaben. Greift auf IReportGenerator.createFor(...) zurück.
*/
public class ReportingService {
 public void printLoanReport(List<Loan> loans) {
     IReportGenerator<Loan> generator = IReportGenerator.createFor(Loan.class);
     IPrinter.out("=== Ausleihreport ===");
     IPrinter.out(generator.generate(loans));
     IPrinter.out("");
 }

 public void printBookReport(List<Book> books) {
     IReportGenerator<Book> generator = IReportGenerator.createFor(Book.class);
     IPrinter.out("=== Bücherreport ===");
     IPrinter.out(generator.generate(books));
     IPrinter.out("");
 }

 public void printMemberReport(List<Member> members) {
     IReportGenerator<Member> generator = IReportGenerator.createFor(Member.class);
     IPrinter.out("=== Mitgliederreport ===");
     IPrinter.out(generator.generate(members));
     IPrinter.out("");
 }
}
