package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.services.exceptions.DatabaseException;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {
	
	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	private MovieEntity movieEntity;
	private MovieDTO movieDTO;
	private String title;
	private Pageable pageable;

	@BeforeEach
	void setUp() throws Exception {
		movieEntity = MovieFactory.createMovieEntity();
		movieDTO = MovieFactory.createMovieDTO();
		title = "Test Movie";
		pageable = PageRequest.of(1, 6, Sort.by("title"));

		// Expected result in find all
		when(repository.searchByTitle(title, pageable)).thenReturn(new PageImpl<>(List.of(movieEntity)));

		// Expected result in find by id
		when(repository.findById(1L)).thenReturn(Optional.of(movieEntity));

		// Expected result in save
		when(repository.save(any(MovieEntity.class))).thenReturn(movieEntity);

		// Expected result in get reference by id
		when(repository.getReferenceById(any(Long.class))).thenReturn(movieEntity);

		// Expected result in delete by id
		doNothing().when(repository).deleteById(any(Long.class));

		// Expected result in existing by id
		when(repository.existsById(1L)).thenReturn(true);
	}

	@Test
	@DisplayName("Find All Movies Should Return Page Movie Data Transfer Object")
	public void findAllShouldReturnPagedMovieDTO() {
		var result = service.findAll(title, pageable);

		verify(repository, times(1))
				.searchByTitle(
						any(String.class), any(Pageable.class)
				);

		assertAll("Verifying all elements are instance of MovieDTO",
			() -> assertTrue(result.getSize() > 0, "Result should not be empty"),
			() -> result.forEach(movie -> {
				assertTrue(movie instanceof MovieDTO);
				assertEquals(movie.getId(), movieDTO.getId());
				assertEquals(movie.getTitle(), movieDTO.getTitle());
				assertEquals(movie.getScore(), movieDTO.getScore());
				assertEquals(movie.getCount(), movieDTO.getCount());
				assertEquals(movie.getImage(), movieDTO.getImage());
			})
		);
	}
	
	@Test
	@DisplayName("Find By Id Movie Should Return Movie DTO Whe Id Exists")
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
		var result = service.findById(1L);

		verify(repository, times(1)).findById(any(Long.class));

		assertTrue(result instanceof MovieDTO);
		assertEquals(result.getId(), movieDTO.getId());
		assertEquals(result.getTitle(), movieDTO.getTitle());
		assertEquals(result.getScore(), movieDTO.getScore());
		assertEquals(result.getCount(), movieDTO.getCount());
		assertEquals(result.getImage(), movieDTO.getImage());
	}
	
	@Test
	@DisplayName("Find By Id Movie Should Throw Resource Not Found Exception When Id Does Not Exists")
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		var noExistingId = 1000L;

		when(repository.findById(noExistingId)).thenReturn(Optional.empty());

		assertThrows(
			ResourceNotFoundException.class,
			() -> service.findById(noExistingId)
		);

		verify(repository, times(1)).findById(any(Long.class));
	}
	
	@Test
	@DisplayName("Insert Movie Should Return Movie DTO")
	public void insertShouldReturnMovieDTO() {
	}
	
	@Test
	@DisplayName("Update Movie Should Return Video DT When Id Exists")
	public void updateShouldReturnMovieDTOWhenIdExists() {
	}
	
	@Test
	@DisplayName("Update Movie Should Throw Resource Not Found Exception When Id Does Not Exist")
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}
	
	@Test
	@DisplayName("Delete Movie Should Do Nothing When Id Exists")
	public void deleteShouldDoNothingWhenIdExists() {
	}
	
	@Test
	@DisplayName("Delete Movie Should Throw Resource Not Found Exception When Id Does Not Exist")
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}
	
	@Test
	@DisplayName("Delete Movie Should Throw Database Exception When Dependent Id")
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
	}
}
