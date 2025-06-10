package interfaces.validators;

import entities.Member;
import exception.MemberEmailInvalidException;
import exception.MemberNameEmptyException;
import exception.MemberNullException;
import global.Result;
import interfaces.IValidator;

public interface MemberValidator extends IValidator<Member> {

    MemberValidator INSTANCE = new MemberValidator() {
        @Override
        public Result<Member, RuntimeException> validateEntity(Member member) {
            Result<Member, RuntimeException> r;

            r = checkNull(member);
            if (r.isError()) return r;

            r = checkName(member);
            if (r.isError()) return r;

            r = checkEmail(member);
            if (r.isError()) return r;

            return Result.success(member);
        }
    };

    static Result<Member, RuntimeException> checkNull(Member member) {
        if (member == null) {
            return Result.error(new MemberNullException());
        }
        return Result.success(member);
    }

    static Result<Member, RuntimeException> checkName(Member member) {
        String name = member.getName();
        if (name == null || name.isBlank()) {
            return Result.error(new MemberNameEmptyException());
        }
        return Result.success(member);
    }

    static Result<Member, RuntimeException> checkEmail(Member member) {
        String email = member.getEmail();
        if (email == null || !email.contains("@") || !email.contains(".")) {
            return Result.error(new MemberEmailInvalidException(email));
        }
        return Result.success(member);
    }
}
