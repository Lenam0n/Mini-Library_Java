package validators;

import entities.Member;
import global.Result;
import interfaces.IValidator;

/**
 * Validiert alle relevanten Felder einer Member-Instanz:
 *  - name darf nicht leer sein
 *  - email muss ein einfaches Format mit "@" und "." haben
 */
public class MemberValidator implements IValidator<Member> {

    @Override
    public Result<Member, String> validate(Member member) {
        if (member == null) {
            return Result.error("Member darf nicht null sein");
        }

        // 1) Name nicht leer
        String name = member.getName();
        if (name == null || name.isBlank()) {
            return Result.error("Name darf nicht leer sein");
        }

        // 2) E-Mail einfach prüfen: muss "@" und "." enthalten
        String email = member.getEmail();
        if (email == null || !email.contains("@") || !email.contains(".")) {
            return Result.error("Ungültige E-Mail: " + email);
        }

        // Alle Prüfungen bestanden
        return Result.success(member);
    }
}
