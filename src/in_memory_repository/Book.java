package in_memory_repository;

import in_memory_repository.interfaces.IEntity;

public class Book implements IEntity<String>{
	private String isbn;
	private String title;
	private String author;
	
	public Book(String isbn, String title,String author) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}
	
    // Getter für weitere Felder
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

	@Override
	public String getId() {
		return this.isbn;
	}

    // Für einfaches Debugging / Ausgabe
    @Override
    public String toString() {
        return String.format("Book[isbn=%s, title=%s, author=%s]", 
                              isbn, title, author);
    }
}
