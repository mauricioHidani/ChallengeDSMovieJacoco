package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;

	@Mock
	private UserService userService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ScoreRepository scoreRepository;

	private UserEntity userEntity;
	private MovieEntity movieEntity;
	private ScoreEntity scoreEntity;
	private ScoreDTO scoreDTO;

	@BeforeEach
	void setUp() throws Exception {
		userEntity = UserFactory.createUserEntity();
		movieEntity = MovieFactory.createMovieEntity();
		scoreEntity = ScoreFactory.createScoreEntity();
		scoreDTO = ScoreFactory.createScoreDTO();

		// Expected result in authenticated
		when(userService.authenticated()).thenReturn(userEntity);

		// Expected result in find by id movie
		when(movieRepository.findById(1L)).thenReturn(Optional.of(movieEntity));

		// Expected result in save and flush
		when(scoreRepository.saveAndFlush(scoreEntity)).thenReturn(scoreEntity);

		// Expected result in save movie
		movieEntity.getScores().add(scoreEntity);
		when(movieRepository.save(movieEntity)).thenReturn(movieEntity);
	}
	
	@Test
	@DisplayName("Save Movie Score Should Return Movie DTO")
	public void saveScoreShouldReturnMovieDTO() {
	}
	
	@Test
	@DisplayName("Save Movie Score Should Throw Resource Not Found Exception When Non Existing Movie Id")
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
	}
}
