package hu.szrnkapeter.monolith.service;

import java.util.List;

public interface AbstractService<T> {
	
	/**
	 * Deletes a book by id
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Returns all books.
	 * 
	 * @return
	 */
	List<T> getAll();
	
	/**
	 * Returns one book by id.
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id);
}