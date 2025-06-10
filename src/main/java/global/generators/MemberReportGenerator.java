package global.generators;

import entities.Member;
import interfaces.IReportGenerator;

/**
 * Generiert einen Report für Member-Objekte aus dem global-Package.
 * Nutzt die Default‐generate(List<T>) im Interface.
 */
public class MemberReportGenerator implements IReportGenerator<Member> {

    public MemberReportGenerator() { }

    @Override
    public String header() {
        return String.format(
            "ID    | Name                           | E-Mail\n" +
            "----------------------------------------------------"
        );
    }

    @Override
    public String formatItem(Member member) {
        // Spaltenbreiten: ID (5), Name (30), E-Mail (variabel)
        String id    = member.getId().toString();
        String name  = member.getName();
        String email = member.getEmail();

        String paddedId = String.format("%-5s", id);
        String paddedName = name.length() > 30
            ? name.substring(0, 27) + "..."
            : String.format("%-30s", name);

        return String.format("%s | %s | %s", paddedId, paddedName, email);
    }

    @Override
    public String footer(int count) {
        return String.format(
            "----------------------------------------------------\n" +
            "Anzahl Mitglieder: %d", count
        );
    }
}
