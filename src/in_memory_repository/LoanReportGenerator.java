package in_memory_repository;

import in_memory_repository.interfaces.IReportGenerator;
import java.time.format.DateTimeFormatter;

public class LoanReportGenerator implements IReportGenerator<Loan> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String header() {
        return ""
	            + "=== Ausleihreport ===\n"
	            + "ISBN           | Member ID | Ausleihdatum\n"
	            + "-----------------------------------------";
    }

    @Override
    public String formatItem(Loan item) {
        return String.format(	"%-14s | %9d | %s",
					            item.getIsbn(),
					            item.getMemberId(),
					            item.getBorrowDate().format(DATE_FORMAT)
					        );
    }

    @Override
    public String footer(int count) { return String.format("Anzahl Ausleihen: %d", count); }
}
