package interfaces.validators;    // oder: validators – passe an dein Projekt an

import java.util.regex.Pattern;

import entities.Book;
import exception.BookAuthorEmptyException;
import exception.BookIsbnInvalidException;
import exception.BookNullException;
import exception.BookTitleEmptyException;
import global.Result;
import interfaces.IValidator;

public interface BookValidator extends IValidator<Book> {

    BookValidator INSTANCE = new BookValidator() {
        @Override
        public Result<Book, RuntimeException> validateEntity(Book book) {
            Result<Book,RuntimeException> r;

            r = checkNull(book);
            if (r.isError()) return r;

            r = checkTitle(book);
            if (r.isError()) return r;

            r = checkAuthor(book);
            if (r.isError()) return r;

            r = checkIsbn(book);
            if (r.isError()) return r;

            return Result.success(book);
        }
    };

    static Result<Book, RuntimeException> checkNull(Book book) {
        if (book == null) {
            return Result.error(new BookNullException());
        }
        return Result.success(book);
    }

    static Result<Book, RuntimeException> checkTitle(Book book) {
        String title = book.getTitle();
        if (title == null || title.isBlank()) {
            return Result.error(new BookTitleEmptyException());
        }
        return Result.success(book);
    }

    static Result<Book, RuntimeException> checkAuthor(Book book) {
        String author = book.getAuthor();
        if (author == null || author.isBlank()) {
            return Result.error(new BookAuthorEmptyException());
        }
        return Result.success(book);
    }

    static Result<Book, RuntimeException> checkIsbn(Book book) {
        String isbn = book.getIsbn();
        Pattern p   = Pattern.compile("^\\d{3}-\\d{10}$");
        if (isbn == null || !p.matcher(isbn).matches()) {
            return Result.error(new BookIsbnInvalidException(isbn));
        }
        return Result.success(book);
    }
}
