package hu.szrnkapeter.monolith.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import hu.szrnkapeter.monolith.dto.BookDto;
import hu.szrnkapeter.monolith.dto.IdResponseDto;
import hu.szrnkapeter.monolith.redis.entity.BookEntity;
import hu.szrnkapeter.monolith.redis.repository.RedisBookRepository;
import hu.szrnkapeter.monolith.utils.Constants;

@ConditionalOnProperty(name = Constants.PARAMETER_DAO_IMPL, havingValue = Constants.DAO_IMPL_REDIS)
@Component
public class BookDaoRedisImpl extends DaoBase<BookEntity> implements BookDao {
	
	@Autowired
	private RedisBookRepository repository;
	
	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.monolith.dao.DaoBase#daoFindById(java.lang.Long)
	 */
	@Override
	protected Optional<BookEntity> daoFindById(Long id) {
		return repository.findById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.monolith.dao.BookDao#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		getByIdOrThrowError(null, id);
		repository.deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.monolith.dao.BookDao#getAll()
	 */
	@Override
	public List<BookDto> getAll() {
		return repository.findAll().stream().map(entity -> convertToDto(entity)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.monolith.dao.BookDao#getById(java.lang.Long)
	 */
	@Override
	public BookDto getById(Long id) {
		Optional<BookEntity> entity = daoFindById(id);

		if(!entity.isPresent()) {
			throw new RuntimeException("Entity does not exists!");
		}

		return convertToDto(entity.get());
	}
	
	/*
	 * (non-Javadoc)
	 * @see hu.szrnkapeter.monolith.dao.BookDao#save(hu.szrnkapeter.monolith.dto.BookDto)
	 */
	@Override
	public IdResponseDto save(BookDto dto) {
		BookEntity entity = new BookEntity();

		getByIdOrThrowError(entity, dto.getId());

		entity.setId(dto.getId());
		entity.setAuthor(dto.getAuthor());
		entity.setReleaseYear(dto.getReleaseYear());
		entity.setTitle(dto.getTitle());
		entity = repository.save(entity);
		
		return new IdResponseDto(entity.getId());
	}

	private BookDto convertToDto(BookEntity entity) {
		BookDto dto = new BookDto();
		dto.setId(entity.getId());
		dto.setAuthor(entity.getAuthor());
		dto.setReleaseYear(entity.getReleaseYear());
		dto.setTitle(entity.getTitle());
		return dto;
	}
}