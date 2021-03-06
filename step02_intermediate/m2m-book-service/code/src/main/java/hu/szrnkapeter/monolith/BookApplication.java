package hu.szrnkapeter.monolith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import hu.szrnkapeter.monolith.dao.BookDao;
import hu.szrnkapeter.monolith.dto.BookDto;

public class BookApplication extends AbstractDemoApplication {

	@Autowired
	private BookDao bookDao;
	
	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		createAndSaveBook(1L, "Author1", "The book title 1", 2001);
		createAndSaveBook(2L, "Author2", "The book title 2", 2002);
		createAndSaveBook(3L, "Author3", "The book title 3", 2003);
	}

	private void createAndSaveBook(Long id,String author, String title, int releaseYear) {
		BookDto dto = new BookDto();
		dto.setAuthor(author);
		dto.setTitle(title);
		dto.setReleaseYear(releaseYear);

		bookDao.save(dto);
	}
}